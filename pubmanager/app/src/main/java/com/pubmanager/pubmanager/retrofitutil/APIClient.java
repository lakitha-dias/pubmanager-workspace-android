package com.pubmanager.pubmanager.retrofitutil;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://localhost:8082/api/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            String BASE_URL = "http://localhost:8082/api/";
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        
        
        return retrofit;
    }

}
