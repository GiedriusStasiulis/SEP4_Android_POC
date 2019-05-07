package com.example.app_v1.apiclients;

import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IAndroidWebApiClient
{
    @GET("measurements")
    @Headers({
            "Content-Type: text/plain;charset=utf-8",
            "Accept: text/plain;charset=utf-8",
    })
    Call<Temperature> measurements();

    @GET("measurements")
    @Headers({
            "Content-Type: text/plain;charset=utf-8",
            "Accept: text/plain;charset=utf-8",
    })
    Call<List<Measurement>> getMeasurements(
            @Query("userName") String userName
    );

    @GET("greenhouses")
    @Headers({
            "Content-Type: text/plain;charset=utf-8",
            "Accept: text/plain;charset=utf-8",
    })

    Call<List<Greenhouse>> getGreenhousesByUser(
            @Query("userId") int userId
    );

    @GET("greenhouses")
    @Headers({
            "Content-Type: text/plain;charset=utf-8",
            "Accept: text/plain;charset=utf-8",
    })

    Call<List<Greenhouse>> getGreenhousesById(
            @Query("id") int greenhouseId
    );

}
