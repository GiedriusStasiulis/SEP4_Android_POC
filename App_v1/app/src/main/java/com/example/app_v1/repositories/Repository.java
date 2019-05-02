package com.example.app_v1.repositories;

import android.arch.lifecycle.LiveData;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Temperature;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Repository
{
    private static Repository instance;

    private LiveData<ArrayList<Greenhouse>> greenhouses;
    private LiveData<Temperature> latestTemperature;
    private LiveData<Humidity> latestHumidity;
    private LiveData<Co2> latestCo2;
    private LiveData<ArrayList<Temperature>> temperatureDataInRange;
    private LiveData<ArrayList<Humidity>> humidityDataInRange;
    private LiveData<ArrayList<Co2>> co2DataInRange;

    public static Repository getInstance()
    {
        if(instance == null)
        {
            instance = new Repository();
        }
        return instance;
    }

    //

    public LiveData<ArrayList<Greenhouse>> getGreenhouses(String userId)
    {
        return this.greenhouses;
    }

    public LiveData<Temperature> getLatestTemperature(String greenhouseId)
    {
        return this.latestTemperature;
    }

    public LiveData<Humidity> getLatestHumidity(String greenhouseId)
    {
        return this.latestHumidity;
    }

    public LiveData<Co2> getLatestCo2(String greenhouseId)
    {
        return this.latestCo2;
    }

    public LiveData<ArrayList<Temperature>> getTemperatureDataInRange(String greenhouseId, Timestamp dateTimeFrom, Timestamp dateTimeTo)
    {
        return this.temperatureDataInRange;
    }

    public LiveData<ArrayList<Humidity>> getHumidityDataInRange(String greenhouseId, Timestamp dateTimeFrom, Timestamp dateTimeTo)
    {
        return this.humidityDataInRange;
    }

    public LiveData<ArrayList<Co2>> getCo2DataInRange(String greenhouseId, Timestamp dateTimeFrom, Timestamp dateTimeTo)
    {
        return this.co2DataInRange;
    }
}
