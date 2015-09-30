package com.pundroid.cargodelivery.Api;


import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by pumba30 on 28.09.2015.
 */
public class RestClientOrdersData {

    public static final String BASE_URL = "http://mobapply.com";
    private static APIService REST_CLIENT;

    private RestClientOrdersData() {
    }

    static {
        setupRestClient();
    }


    public static APIService getInstance() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(APIService.class);
    }


}


