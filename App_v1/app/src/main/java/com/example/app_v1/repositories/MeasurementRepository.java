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
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementRepository
{
    private static MeasurementRepository instance;

    private MutableLiveData<ArrayList<Measurement>> latestMeasurementsFromApi = new MutableLiveData<>();
    private ArrayList<Measurement> latestMeasurementsArrList = new ArrayList<>();
    private ArrayList<Measurement> measurementsInDateRange = new ArrayList<>();
    private ArrayList<Temperature> temperaturesInDateRange = new ArrayList<>();
    private ArrayList<Humidity> humidityInDateRange = new ArrayList<>();
    private ArrayList<Co2> co2InDateRange = new ArrayList<>();

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
                        //Handle no connection to API
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

    public ArrayList<Measurement> getMeasurementsInDateRange(String dateTimeFrom, String dateTimeTo)
    {
        String iso8601dateTimeFrom = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeFrom);
        String iso8601dateTimeTo = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeTo);

        //Call retrofit and pass the formatted timestamps as args

        return this.measurementsInDateRange;
    }

    public ArrayList<Temperature> getTemperaturesInDateRange(String dateTimeFrom, String dateTimeTo)
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        temperaturesInDateRange.clear();

        Date dateFrom = DateTimeConverterHelper.convertDateTimeStringToDate(dateTimeFrom);
        Date dateTo = DateTimeConverterHelper.convertDateTimeStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < latestMeasurementsArrList.size(); i++)
        {
            Date measurementDate = DateTimeConverterHelper.convertTimestampISO8601StringToDate(latestMeasurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Temperature temperature = new Temperature(String.valueOf(latestMeasurementsArrList.get(i).getTemperature()));
                temperature.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(latestMeasurementsArrList.get(i).getTimeStamp()));
                temperature.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(latestMeasurementsArrList.get(i).getTimeStamp()));

                temperaturesInDateRange.add(temperature);
            }
        }

        return this.temperaturesInDateRange;
    }

    public ArrayList<Humidity> getHumidityInDateRange(String dateTimeFrom, String dateTimeTo)
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        humidityInDateRange.clear();

        Date dateFrom = DateTimeConverterHelper.convertDateTimeStringToDate(dateTimeFrom);
        Date dateTo = DateTimeConverterHelper.convertDateTimeStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < latestMeasurementsArrList.size(); i++)
        {
            Date measurementDate = DateTimeConverterHelper.convertTimestampISO8601StringToDate(latestMeasurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Humidity humidity = new Humidity(String.valueOf(latestMeasurementsArrList.get(i).getHumidity()));
                humidity.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(latestMeasurementsArrList.get(i).getTimeStamp()));
                humidity.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(latestMeasurementsArrList.get(i).getTimeStamp()));

                humidityInDateRange.add(humidity);
            }
        }

        return this.humidityInDateRange;
    }

    public ArrayList<Co2> getCo2InDateRange(String dateTimeFrom, String dateTimeTo)
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        co2InDateRange.clear();

        Date dateFrom = DateTimeConverterHelper.convertDateTimeStringToDate(dateTimeFrom);
        Date dateTo = DateTimeConverterHelper.convertDateTimeStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < latestMeasurementsArrList.size(); i++)
        {
            Date measurementDate = DateTimeConverterHelper.convertTimestampISO8601StringToDate(latestMeasurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Co2 co2 = new Co2(String.valueOf(latestMeasurementsArrList.get(i).getcO2()));
                co2.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(latestMeasurementsArrList.get(i).getTimeStamp()));
                co2.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(latestMeasurementsArrList.get(i).getTimeStamp()));

                co2InDateRange.add(co2);
            }
        }

        return this.co2InDateRange;
    }
}
