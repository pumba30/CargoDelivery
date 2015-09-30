package com.pundroid.cargodelivery.Api;

import com.pundroid.cargodelivery.pojo.geocodingData.ResultRequestGeocodingApi;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by pumba30 on 28.09.2015.
 */
public interface APIService {
    // for example
    //http://mobapply.com/tests/orders/
    @GET("/tests/orders/")
    void getOrdersData(Callback<ArrayList> callback);

    // for example
    // https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY
    @GET("/maps/api/geocode/json")
    void getResultsQuery(@Query("address") String address,
                         @Query("key") String key,
                         Callback<ResultRequestGeocodingApi> cb);

}
