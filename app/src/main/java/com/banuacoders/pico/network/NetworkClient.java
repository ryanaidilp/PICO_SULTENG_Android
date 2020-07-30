package com.banuacoders.pico.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final String BASE_URL_API = "https://banuacoders.com/api/pico/v2/";
    private static NetworkClient mInstance;
    private Retrofit retrofitCoder;

    private NetworkClient() {
        retrofitCoder = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized NetworkClient getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkClient();
        }
        return mInstance;
    }
    public Api getApiCoder() {
        return retrofitCoder.create(Api.class);
    }
}
