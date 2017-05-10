package com.vaibhav.hotstock.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://192.168.0.10:8080/api/hotstock/v1/";
    private static ApiClient apiClient;

    ApiInterface service;


    public static ApiClient getInstance() {

        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public ApiClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        service = retrofit.create(ApiInterface.class);
    }


    public ApiInterface getService() {
        return service;
    }


}
