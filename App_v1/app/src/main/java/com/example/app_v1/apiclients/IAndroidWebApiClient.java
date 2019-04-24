package com.example.app_v1.apiclients;

import com.example.app_v1.models.Temperature;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface IAndroidWebApiClient
{
    @GET("measurements/")
    @Headers({
            "Content-Type: text/plain;charset=utf-8",
            "Accept: text/plain;charset=utf-8",
    })
    Call<Temperature> measurements();
}
