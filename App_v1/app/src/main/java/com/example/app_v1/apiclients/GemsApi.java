package com.example.app_v1.apiclients;

import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.ThresholdInterval;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PUT;
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
    Call<ArrayList<Measurement>> getMeasurementsInDateRange(
            @Query("greenhouseId") int greenhouseId,
            @Query(value = "from", encoded = true) String dateTimeFrom,
            @Query(value = "to", encoded = true) String dateTimeTo
    );

    // thresholds
    @GET("Thresholds/{greenhouse}/{measurement}")
    Call<ThresholdInterval> getThreshold(
            @Path("greenhouse") int id,
            @Path("measurement") String measurement
    );

    @PUT("Thresholds/{greenhouse}/{measurement}")
    Call<ResponseBody> setThreshold(
            @Path("greenhouse") int id,
            @Path("measurement") String measurement,
            @Query("min") int minValue,
            @Query("max") int maxValue
    );

}