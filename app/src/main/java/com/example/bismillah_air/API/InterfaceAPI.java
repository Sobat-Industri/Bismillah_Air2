package com.example.bismillah_air.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceAPI {

    String BASE_URL = "https://marleth.000webhostapp.com/api/";

    @GET("send-mesin/{id}")
    Call<Respon> rp(@Path("id") String id);

    @GET("history/6")
    Call<List<History>> getHistory();
}
