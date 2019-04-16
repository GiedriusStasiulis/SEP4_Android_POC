package com.example.app_v1.apiclients;

import com.example.app_v1.models.Temperature;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface IAndroidWebApiClient
{
    String BASE_URL = "https://192.168.1.98:44398/";

    @Headers("Content-Type: application/json")
    @GET("api/values")
    Call<Temperature> getData();
}
