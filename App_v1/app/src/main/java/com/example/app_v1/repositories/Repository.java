package com.example.app_v1.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
    private MutableLiveData<Temperature> latestTemperature = new MutableLiveData<>();
    private LiveData<Humidity> latestHumidity;
    private LiveData<Co2> latestCo2;
    private MutableLiveData<ArrayList<Temperature>> temperatureDataInRange = new MutableLiveData<>();
    private LiveData<ArrayList<Humidity>> humidityDataInRange;
    private LiveData<ArrayList<Co2>> co2DataInRange;

    ArrayList<Temperature> temperatures = new ArrayList<>();

    public static Repository getInstance()
    {
        if(instance == null)
        {
            instance = new Repository();
        }

        return instance;
    }

    public LiveData<ArrayList<Greenhouse>> getGreenhouses()
    {
        return this.greenhouses;
    }

    public LiveData<Temperature> getLatestTemperature()
    {
        return this.latestTemperature;
    }

    public LiveData<Humidity> getLatestHumidity()
    {
        return this.latestHumidity;
    }

    public LiveData<Co2> getLatestCo2()
    {
        return this.latestCo2;
    }

    public LiveData<ArrayList<Temperature>> getTemperatureDataInRange()
    {
        return this.temperatureDataInRange;
    }

    public LiveData<ArrayList<Humidity>> getHumidityDataInRange()
    {
        return this.humidityDataInRange;
    }

    public LiveData<ArrayList<Co2>> getCo2DataInRange(String greenhouseId, Timestamp dateTimeFrom, Timestamp dateTimeTo)
    {
        return this.co2DataInRange;
    }

    public void addDummyTemps()
    {
        Temperature temp = new Temperature("25.7","12:00 / 05-May-19");
        Temperature temp2 = new Temperature("24.7","11:50 / 05-May-19");
        Temperature temp3 = new Temperature("25.2","11:40 / 05-May-19");
        Temperature temp4 = new Temperature("26.4","11:30 / 05-May-19");
        Temperature temp5 = new Temperature("26.6","11:20 / 05-May-19");
        Temperature temp6 = new Temperature("28.7","11:10 / 05-May-19");
        Temperature temp7 = new Temperature("24.3","11:00 / 05-May-19");
        Temperature temp8 = new Temperature("26.9","10:50 / 05-May-19");
        Temperature temp9 = new Temperature("25.5","10:40 / 05-May-19");

        Temperature temp10 = new Temperature("25.7","10:30 / 05-May-19");
        Temperature temp11 = new Temperature("24.7","10:20 / 05-May-19");
        Temperature temp12 = new Temperature("25.2","10:10 / 05-May-19");
        Temperature temp13 = new Temperature("26.4","10:00 / 05-May-19");
        Temperature temp14 = new Temperature("26.6","09:50 / 05-May-19");
        Temperature temp15 = new Temperature("28.7","09:40 / 05-May-19");
        Temperature temp16 = new Temperature("24.3","09:30 / 05-May-19");
        Temperature temp17 = new Temperature("26.9","09:20 / 05-May-19");
        Temperature temp18 = new Temperature("25.5","09:10 / 05-May-19");
        Temperature temp19 = new Temperature("26.5","09:00 / 05-May-19");
        Temperature temp20 = new Temperature("27.5","08:50 / 05-May-19");

        Temperature temp21 = new Temperature("25.7","08:40 / 05-May-19");
        Temperature temp22 = new Temperature("24.7","08:30 / 05-May-19");
        Temperature temp23 = new Temperature("25.2","08:20 / 05-May-19");
        Temperature temp24 = new Temperature("26.4","08:10 / 05-May-19");
        Temperature temp25 = new Temperature("26.6","08:00 / 05-May-19");
        Temperature temp26 = new Temperature("28.7","07:50 / 05-May-19");
        Temperature temp27 = new Temperature("24.3","07:40 / 05-May-19");
        Temperature temp28 = new Temperature("26.9","07:30 / 05-May-19");
        Temperature temp29 = new Temperature("25.5","07:20 / 05-May-19");
        Temperature temp30 = new Temperature("26.5","07:10 / 05-May-19");
        Temperature temp31 = new Temperature("27.5","07:00 / 05-May-19");

        temperatures.add(temp);
        temperatures.add(temp2);
        temperatures.add(temp3);
        temperatures.add(temp4);
        temperatures.add(temp5);
        temperatures.add(temp6);
        temperatures.add(temp7);
        temperatures.add(temp8);
        temperatures.add(temp9);
        temperatures.add(temp10);
        temperatures.add(temp11);
        temperatures.add(temp12);
        temperatures.add(temp13);
        temperatures.add(temp14);
        temperatures.add(temp15);
        temperatures.add(temp16);
        temperatures.add(temp17);
        temperatures.add(temp18);
        temperatures.add(temp19);
        temperatures.add(temp20);
        temperatures.add(temp21);
        temperatures.add(temp22);
        temperatures.add(temp23);
        temperatures.add(temp24);
        temperatures.add(temp25);
        temperatures.add(temp26);
        temperatures.add(temp27);
        temperatures.add(temp28);
        temperatures.add(temp29);
        temperatures.add(temp30);
        temperatures.add(temp31);

        temperatureDataInRange.setValue(temperatures);
        latestTemperature.setValue(temperatures.get(0));
    }
}