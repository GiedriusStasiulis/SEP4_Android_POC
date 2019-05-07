package com.example.app_v1.requests;

import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Measurement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GemsApi {

    @GET("/api/measurements")
    Call<List<Measurement>> getMeasurements(
        @Query("userName") String userName
    );

    @GET("/api/greenhouses")
    Call<List<Greenhouse>> getGreenhousesByUser(
            @Query("userId") int userId
    );

    @GET("/api/greenhouses")
    Call<List<Greenhouse>> getGreenhousesById(
            @Query("id") int greenhouseId
    );
}