package com.example.app_v1.apiclients;

import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Measurement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GemsApi
{
    // greenhouses

    @GET("Greenhouses")
    Call<List<Greenhouse>> getAllGreenhouses();

    @GET("Greenhouses/{id}")
    Call<Greenhouse> getGreenhouse(@Path("id")int id);

    // measurements

    @GET("Measurements")
    Call<ArrayList<Measurement>> getMeasurement(@Query("greenhouseId") int greenhouseId);

    @GET("Measurements")
    Call<List<Measurement>> getMeasurementInterval(
            @Field("greenhouseId") int greenhouseId,
            @Field("from") String dateTimeFrom,
            @Field("to") String dateTimeTo
    );
}