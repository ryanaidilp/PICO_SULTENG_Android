package com.banuacoders.pico.network;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("query")
    Call<ResponseBody> getCovidStats(
            @QueryMap Map<String, Object> queryMap
    );

    @GET("kabupaten")
    Call<ResponseBody> getAllCity();

    @GET("rumahsakit")
    Call<ResponseBody> getAllHospital();

    @GET("provinsi")
    Call<ResponseBody> getAllProvince();
}
