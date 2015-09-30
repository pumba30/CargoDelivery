package com.pundroid.cargodelivery.Api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by pumba30 on 30.09.2015.
 */
public class RestClientGeoCodingApi {

    public static final String BASE_URL_GEOCODING = "https://maps.googleapis.com";
    private static APIService REST_CLIENT;

    private RestClientGeoCodingApi() {
    }

    static {
        setupRestClient();
    }


    public static APIService getInstance() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL_GEOCODING)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(APIService.class);
    }
}
