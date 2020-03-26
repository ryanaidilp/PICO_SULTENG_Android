package com.banuacoders.covidcheck.network;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final String BASE_URL_STATS = "https://services5.arcgis.com/VS6HdKS0VfIhv8Ct/arcgis/rest/services/Statistik_Perkembangan_COVID19_Indonesia/FeatureServer/0/";
    private static final String BASE_URL_API = "https://banuacoders.com/api/pico/";
    private static NetworkClient mInstance;
    private Retrofit retrofitStats, retrofitCoder;

    private NetworkClient() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("where", "1%3D1");
        queryMap.put("outFields", "*");
        queryMap.put("outSR", 4326);
        queryMap.put("f", "json");
        retrofitStats = new Retrofit.Builder()
                .baseUrl(BASE_URL_STATS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .build();
            return chain.proceed(request);
        });
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

    public Api getApiStats() {
        return retrofitStats.create(Api.class);
    }

    public Api getApiCoder() {
        return retrofitCoder.create(Api.class);
    }
}
