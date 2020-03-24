package com.banuacoders.covidcheck.network;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final String BASE_URL_STATS = "https://services5.arcgis.com/VS6HdKS0VfIhv8Ct/arcgis/rest/services/Statistik_Perkembangan_COVID19_Indonesia/FeatureServer/0/";
    private static final String BASE_URL_PROVINCE = "https://services5.arcgis.com/VS6HdKS0VfIhv8Ct/arcgis/rest/services/COVID19_Indonesia_per_Provinsi/FeatureServer/0/";
    private static final String BASE_URL_KABUPATEN = "https://de7fbb85-640c-4eb8-8d73-7d6a41582896.mock.pstmn.io/";
    private static final String API_KEY = "THIS_IS_API_KEY";
    private static NetworkClient mInstance;
    private Retrofit retrofitStats, retrofitProvince, retrofitCity;
    Map<String, Object> queryMap = new HashMap<>();

    private NetworkClient() {
        queryMap.put("where", "1%3D1");
        queryMap.put("outFields", "*");
        queryMap.put("outSR", 4326);
        queryMap.put("f", "json");
        retrofitStats = new Retrofit.Builder()
                .baseUrl(BASE_URL_STATS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitProvince = new Retrofit.Builder()
                .baseUrl(BASE_URL_PROVINCE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("X-API-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        });
        retrofitCity = new Retrofit.Builder()
                .baseUrl(BASE_URL_KABUPATEN)
                .client(httpClient.build())
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

    public Api getApiProvince() {
        return retrofitProvince.create(Api.class);
    }

    public Api getApiKabupaten() {
        return retrofitCity.create(Api.class);
    }
}
