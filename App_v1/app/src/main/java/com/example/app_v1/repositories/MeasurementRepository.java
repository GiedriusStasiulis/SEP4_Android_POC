package com.example.app_v1.repositories;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_v1.apiclients.GemsApi;
import com.example.app_v1.apiclients.GemsApiClient;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.utils.DateTimeConverterHelper;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementRepository
{
    private static MeasurementRepository instance;

    private MutableLiveData<ArrayList<Measurement>> latestMeasurementsFromApi = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Measurement>> measurementsInDateRangeFromApi = new MutableLiveData<>();
    private ArrayList<Measurement> latestMeasurementsArrList = new ArrayList<>();
    private ArrayList<Measurement> measurementsInDateRangeArrList = new ArrayList<>();

    private Runnable fetchDataFromApiRunnable;
    private Handler fetchDataFromApiHandler = new Handler();

    public static MeasurementRepository getInstance()
    {
        if (instance == null)
        {
            instance = new MeasurementRepository();
        }
        return instance;
    }

    public void startFetchingDataFromApi(int greenhouseId)
    {
        fetchDataFromApiRunnable = getFetchDataFromApiRunnable(greenhouseId);
        fetchDataFromApiRunnable.run();
    }

    public void stopFetchingDataFromApi()
    {
        latestMeasurementsArrList = new ArrayList<>();
        latestMeasurementsFromApi.postValue(latestMeasurementsArrList);
        fetchDataFromApiHandler.removeCallbacks(fetchDataFromApiRunnable);
    }

    private Runnable getFetchDataFromApiRunnable(final int greenhouseId)
    {
        final GemsApi gemsApi = GemsApiClient.getRetrofitClient().create(GemsApi.class);

        return new Runnable()
        {
            @Override
            public void run()
            {
                Call<ArrayList<Measurement>> call = gemsApi.getMeasurement(greenhouseId);

                call.enqueue(new Callback<ArrayList<Measurement>>()
                {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<Measurement>> call, @NonNull Response<ArrayList<Measurement>> response)
                    {
                        response.body();

                        if (response.isSuccessful())
                        {
                            Log.d("OnSuccess", "onResponse: " + response.toString());
                            assert response.body() != null;

                            latestMeasurementsArrList.clear();
                            latestMeasurementsArrList = response.body();

                            latestMeasurementsFromApi.postValue(latestMeasurementsArrList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<Measurement>> call, @NonNull Throwable t)
                    {
                        Log.e("OnFailure", "Failure: " + t.getMessage() + " , StackTrace: " + t.getLocalizedMessage());
                    }
                });

                fetchDataFromApiHandler.postDelayed(this,60000);
            }
        };
    }

    public LiveData<ArrayList<Measurement>> getLatestMeasurementsFromApi()
    {
        return this.latestMeasurementsFromApi;
    }

    public LiveData<ArrayList<Measurement>> getMeasurementsInDateRange(int greenhouseId, String dateTimeFrom, String dateTimeTo)
    {
        String iso8601dateTimeFrom = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeFrom);
        String iso8601dateTimeTo = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeTo);

        final GemsApi gemsApi = GemsApiClient.getRetrofitClient().create(GemsApi.class);

        Call<ArrayList<Measurement>> call = gemsApi.getMeasurementsInDateRange(greenhouseId,iso8601dateTimeFrom,iso8601dateTimeTo);

        call.enqueue(new Callback<ArrayList<Measurement>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Measurement>> call, Response<ArrayList<Measurement>> response)
            {
                response.body();

                if(response.isSuccessful())
                {
                    Log.d("OnSuccess", "onResponse: " + response.toString());
                    assert response.body() != null;

                    measurementsInDateRangeArrList.clear();
                    measurementsInDateRangeArrList = response.body();

                    measurementsInDateRangeFromApi.postValue(measurementsInDateRangeArrList);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Measurement>> call, Throwable t)
            {
                Log.e("OnFailure", "Failure: " + t.getMessage() + " , StackTrace: " + t.getLocalizedMessage());
            }
        });

        return this.measurementsInDateRangeFromApi;
    }

    public ArrayList<Temperature> extractLatestTemperaturesFromMeasurements(ArrayList<Measurement> measurements)
    {
        ArrayList<Temperature> latestTemperaturesArrList;

        if(!measurements.isEmpty())
        {
            latestTemperaturesArrList = new ArrayList<>();

            for (int i = 0; i < measurements.size(); i++)
            {
                Temperature temperature = new Temperature(String.valueOf(measurements.get(i).getTemperature()));
                temperature.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(measurements.get(i).getTimeStamp()));
                temperature.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(measurements.get(i).getTimeStamp()));

                latestTemperaturesArrList.add(temperature);
            }
        }
        else
        {
            latestTemperaturesArrList = new ArrayList<>();
        }
        return latestTemperaturesArrList;
    }

    public ArrayList<Humidity> extractLatestHumidityFromMeasurements(ArrayList<Measurement> measurements)
    {
        ArrayList<Humidity> latestHumidityArrList;

        if(!measurements.isEmpty())
        {
            latestHumidityArrList = new ArrayList<>();

            for (int i = 0; i < measurements.size(); i++)
            {
                Humidity humidity = new Humidity(String.valueOf(measurements.get(i).getHumidity()));
                humidity.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(measurements.get(i).getTimeStamp()));
                humidity.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(measurements.get(i).getTimeStamp()));

                latestHumidityArrList.add(humidity);
            }
        }
        else
        {
            latestHumidityArrList = new ArrayList<>();
        }
        return latestHumidityArrList;
    }

    public ArrayList<Co2> extractLatestCo2FromMeasurements(ArrayList<Measurement> measurements)
    {
        ArrayList<Co2> latestCo2ArrList;

        if(!measurements.isEmpty())
        {
            latestCo2ArrList = new ArrayList<>();

            for (int i = 0; i < measurements.size(); i++)
            {
                Co2 co2 = new Co2(String.format(Locale.ENGLISH,"%.0f",measurements.get(i).getcO2()));
                co2.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(measurements.get(i).getTimeStamp()));
                co2.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(measurements.get(i).getTimeStamp()));

                latestCo2ArrList.add(co2);
            }
        }
        else
        {
            latestCo2ArrList = new ArrayList<>();
        }
        return latestCo2ArrList;
    }
}
