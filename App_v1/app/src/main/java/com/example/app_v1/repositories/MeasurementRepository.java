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
import com.example.app_v1.utils.DTimeFormatHelper;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementRepository
{
    private static MeasurementRepository instance;

    private MutableLiveData<ArrayList<Measurement>> latestMeasurementsFromApi = new MutableLiveData<>();
    private ArrayList<Measurement> measurementsArrList = new ArrayList<>();

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
        measurementsArrList = new ArrayList<>();
        latestMeasurementsFromApi.postValue(measurementsArrList);
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

                            measurementsArrList.clear();
                            measurementsArrList = response.body();

                            latestMeasurementsFromApi.postValue(measurementsArrList);
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
        String iso8601dateTimeFrom = DTimeFormatHelper.convertStringDateTimeToISO8601String(dateTimeFrom);
        String iso8601dateTimeTo = DTimeFormatHelper.convertStringDateTimeToISO8601String(dateTimeTo);

        //Call retrofit and pass the formatted timestamps as args

        return this.measurementsInDateRange;
    }

    public ArrayList<Temperature> getTemperaturesInDateRange(String dateTimeFrom, String dateTimeTo)
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        temperaturesInDateRange.clear();

        Date dateFrom = DTimeFormatHelper.convertStringToDate(dateTimeFrom);
        Date dateTo = DTimeFormatHelper.convertStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < measurementsArrList.size(); i++)
        {
            Date measurementDate = DTimeFormatHelper.convertISO8601stringToDate(measurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Temperature temperature = new Temperature(String.valueOf(measurementsArrList.get(i).getTemperature()));
                temperature.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));
                temperature.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));

                temperaturesInDateRange.add(temperature);
            }
        }

        return this.temperaturesInDateRange;
    }

    public ArrayList<Humidity> getHumidityInDateRange(String dateTimeFrom, String dateTimeTo)
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        humidityInDateRange.clear();

        Date dateFrom = DTimeFormatHelper.convertStringToDate(dateTimeFrom);
        Date dateTo = DTimeFormatHelper.convertStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < measurementsArrList.size(); i++)
        {
            Date measurementDate = DTimeFormatHelper.convertISO8601stringToDate(measurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Humidity humidity = new Humidity(String.valueOf(measurementsArrList.get(i).getHumidity()));
                humidity.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));
                humidity.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));

                humidityInDateRange.add(humidity);
            }
        }

        return this.humidityInDateRange;
    }

    public ArrayList<Co2> getCo2InDateRange(String dateTimeFrom, String dateTimeTo)
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        co2InDateRange.clear();

        Date dateFrom = DTimeFormatHelper.convertStringToDate(dateTimeFrom);
        Date dateTo = DTimeFormatHelper.convertStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < measurementsArrList.size(); i++)
        {
            Date measurementDate = DTimeFormatHelper.convertISO8601stringToDate(measurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Co2 co2 = new Co2(String.valueOf(measurementsArrList.get(i).getcO2()));
                co2.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));
                co2.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));

                co2InDateRange.add(co2);
            }
        }

        return this.co2InDateRange;
    }
}
