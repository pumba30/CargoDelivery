package com.pundroid.cargodelivery;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.internal.LinkedTreeMap;
import com.pundroid.cargodelivery.Api.RestClientGeoCodingApi;
import com.pundroid.cargodelivery.Api.RestClientOrdersData;
import com.pundroid.cargodelivery.pojo.geocodingData.Geometry;
import com.pundroid.cargodelivery.pojo.geocodingData.ResultRequestGeocodingApi;
import com.pundroid.cargodelivery.pojo.geocodingData.Results;
import com.pundroid.cargodelivery.pojo.orderData.DepartureAddress;
import com.pundroid.cargodelivery.pojo.orderData.DestinationAddress;
import com.pundroid.cargodelivery.pojo.orderData.Order;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final String GERMANY = "Germany";
    private static final String FROM_PLACE = "from_place";
    private static final String TO_PLACE = "to_place";

    // TIME_OUT - It is used to reduce the number requests to server GoogleMap,
    // since a large number of requests return "OVER_QUERY_LIMIT"
    public static final int TIME_OUT = 90;

    private static String API_KEY = "API_KEY";

    private List<Order> mOrderList = new ArrayList<>();
    private List mOrderDataList = new ArrayList<>();

    private GoogleApiClient mGoogleApiClient;
    private SupportMapFragment mMap;
    private Location mCurrentLocation;
    private LatLng latLngFrom;
    private LatLng latLngTo;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!isConnected()) {
            Toast.makeText(MainActivity.this,
                    R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }


        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrdersData();

    }

    private void getOrdersData() {
        RestClientOrdersData.getInstance().getOrdersData(new Callback<ArrayList>() {
            @Override
            public void success(ArrayList arrayList, Response response) {
                Log.d(TAG, "Success");
                mOrderDataList = arrayList;

                for (Object object : mOrderDataList) {
                    JSONObject jsonObject = convertKeyValueToJSON((LinkedTreeMap<String, Object>) object);
                    Order order = null;
                    try {
                        order = createOrderFromJSON(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mOrderList.add(order);
                }
                //create markers on a map
                createMarkers(mOrderList);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Error");
            }
        });
    }


    /* Create markers for departureAddress (green color) and destinationAddress (red color)
     *
     */
    private void createMarkers(final List<Order> orderList) {

        if (orderList != null && orderList.size() > 0) {
            for (int i = 0; i < orderList.size(); i++) {
                final Order order = orderList.get(i);

                String addressFrom = buildStringRequestAddress(order, FROM_PLACE);
                String addressTo = buildStringRequestAddress(order, TO_PLACE);

                Log.d(TAG, "addressFrom: " + addressFrom);
                Log.d(TAG, "addressFrom: " + addressTo);

                SystemClock.sleep(TIME_OUT);
                RestClientGeoCodingApi.getInstance().getResultsQuery(addressFrom,
                        API_KEY, new Callback<ResultRequestGeocodingApi>() {
                            @Override
                            public void success(ResultRequestGeocodingApi resultRequestGeocodingApi, Response response) {
                                // status of request
                                String resultStatus = resultRequestGeocodingApi.getStatus();

                                //get results from request
                                if (resultStatus.equalsIgnoreCase("ok")) {
                                    List<Results> results = resultRequestGeocodingApi.getResults();
                                    addMarkerToMap(results, BitmapDescriptorFactory.HUE_GREEN);
                                    latLngFrom = getLocationMarker(results);

                                    Log.d(TAG, "STATUS RESULTS : " + resultStatus);
                                } else {
                                    Log.d(TAG, "STATUS ERROR: " + resultStatus);
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d(TAG, "Error!");
                            }
                        });

                SystemClock.sleep(TIME_OUT);
                final int finalI = i;
                RestClientGeoCodingApi.getInstance().getResultsQuery(addressTo,
                        API_KEY, new Callback<ResultRequestGeocodingApi>() {
                            @Override
                            public void success(ResultRequestGeocodingApi resultRequestGeocodingApi, Response response) {
                                // status of request
                                String resultStatus = resultRequestGeocodingApi.getStatus();
                                //get results from request
                                if (resultStatus.equalsIgnoreCase("ok")) {
                                    List<Results> results = resultRequestGeocodingApi.getResults();
                                    addMarkerToMap(results, BitmapDescriptorFactory.HUE_RED);
                                    latLngTo = getLocationMarker(results);

                                    addPolyLine(latLngFrom, latLngTo);
                                    Log.d(TAG, "STATUS RESULTS : " + resultStatus);
                                } else {
                                    Log.d(TAG, "STATUS ERROR: " + resultStatus);
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d(TAG, "Error!");
                            }
                        });


            }
        }
    }

    // add line between markers
    private void addPolyLine(LatLng from, LatLng to) {
        if (from != null || to != null) {
            mMap.getMap().addPolyline(new PolylineOptions()
                    .add(from, to)
                    .width(3)
                    .color(Color.RED));
        }
    }

    private void addMarkerToMap(List<Results> results, float color) {

        LatLng latLng = getLocationMarker(results);
        final MarkerOptions options = new MarkerOptions().position(latLng);
        options.icon(BitmapDescriptorFactory.defaultMarker(color));

        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.addMarker(options);
            }
        });
    }

    private LatLng getLocationMarker(List<Results> results) {
        Geometry geometry = results.get(0).getGeometry();
        com.pundroid.cargodelivery.pojo.geocodingData.Location location = geometry.getLocation();
        double lat = location.getLat();
        double lon = location.getLng();

        return new LatLng(lat, lon);
    }


    // build address for request
    private String buildStringRequestAddress(Order order, String place) {
        StringBuilder builder = new StringBuilder();

        String fromZipCode;
        String fromSity;
        String fromCountryCode;

        if (place.equalsIgnoreCase(FROM_PLACE)) {
            fromZipCode = order.getDepartureAddress().getZipCode();
            fromSity = order.getDepartureAddress().getCity();
            fromCountryCode = order.getDepartureAddress().getCountryCode();
        } else {
            fromZipCode = order.getDestinationAddress().getZipCode();
            fromSity = order.getDestinationAddress().getCity();
            fromCountryCode = order.getDestinationAddress().getCountryCode();
        }

        builder.append(fromZipCode + ",")
                .append("+" + fromSity + ",")
                .append("+" + fromCountryCode)
                .toString();

        Log.d(TAG, builder.toString());

        return builder.toString();
    }


    private Order createOrderFromJSON(JSONObject jsonObject) throws JSONException {
        JSONObject joDestAddress = new JSONObject(jsonObject.getString("destinationAddress"));
        JSONObject joDepartAddress = new JSONObject(jsonObject.getString("departureAddress"));

        DepartureAddress departureAddress = new DepartureAddress();

        departureAddress.setCity(joDepartAddress.optString("city", ""));
        departureAddress.setCountry(joDepartAddress.optString("country", ""));
        departureAddress.setCountryCode(joDepartAddress.optString("countryCode", ""));
        departureAddress.setStreet(joDepartAddress.optString("street", ""));
        departureAddress.setHouseNumber(joDepartAddress.optString("houseNumber", ""));
        departureAddress.setZipCode(joDepartAddress.optString("zipCode", ""));

        DestinationAddress destinationAddress = new DestinationAddress();

        destinationAddress.setCity(joDestAddress.optString("city", ""));
        destinationAddress.setCountry(joDestAddress.optString("country", ""));
        destinationAddress.setCountryCode(joDestAddress.optString("countryCode", ""));
        destinationAddress.setStreet(joDestAddress.optString("street", ""));
        destinationAddress.setHouseNumber(joDestAddress.optString("houseNumber", ""));
        destinationAddress.setZipCode(joDestAddress.optString("zipCode", ""));


        Order order = new Order();

        order.setUuid(jsonObject.optString("uuid", ""));
        order.setNumber(jsonObject.optString("number", ""));
        order.setActualWeight(jsonObject.optLong("actualWeight", 0L));
        order.setAppointmentFrom(jsonObject.optLong("appointmentFrom", 0L));
        order.setGood(jsonObject.optString("good", ""));
        order.setInitialPrice(jsonObject.optLong("initialPrice", 0L));
        order.setNote1(jsonObject.optString("note1", ""));
        order.setStatus(jsonObject.optString("status", ""));
        order.setDepartureAddress(departureAddress);
        order.setDestinationAddress(destinationAddress);

        return order;
    }

    private JSONObject convertKeyValueToJSON(LinkedTreeMap<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        Object[] objects = map.entrySet().toArray();
        for (int i = 0; i < objects.length; i++) {
            Map.Entry object = (Map.Entry) objects[i];
            try {
                if (object.getValue() instanceof LinkedTreeMap)
                    jsonObject.put(object.getKey().toString(),
                            convertKeyValueToJSON((LinkedTreeMap<String, Object>) object.getValue()));
                else
                    jsonObject.put(object.getKey().toString(), object.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = getDefaultLocation();
        initCamera(mCurrentLocation);
    }

    /*
     * Get the default location
     */
    private Location getDefaultLocation() {
        Location locationGermany = new Location("");

        // If Google Play Services is available
        if (servicesConnected()) {
            // Get the current location
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            String nameCountry = GERMANY;

            List<Address> results = new ArrayList<>();
            try {
                results = geocoder.getFromLocationName(nameCountry, 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            double lat = results.get(0).getLatitude();
            double lon = results.get(0).getLongitude();
            locationGermany.setLongitude(lon);
            locationGermany.setLatitude(lat);
        }
        return locationGermany;
    }

    /*
    * Verify that Google Play services is available before making a request.
    *
    * @return true if Google Play services is available, otherwise false
    */
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
            // Google Play services was not available for some reason
        } else {
            Toast.makeText(MainActivity.this, "Google Play services is not available", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(6.5f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mMap.getMap().animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        mMap.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getMap().setOnInfoWindowClickListener(this);
        mMap.getMap().getUiSettings().setZoomControlsEnabled(true);
    }


    @Override
    public void onConnectionSuspended(int i) {
        //not implemented
    }

    @Override
    public void onLocationChanged(Location location) {
        //not implemented
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //not implemented
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //not implemented
    }


}
