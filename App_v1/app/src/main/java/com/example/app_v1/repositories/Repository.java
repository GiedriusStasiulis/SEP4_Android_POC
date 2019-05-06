package com.example.app_v1.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Measurement;
import java.util.ArrayList;

public class Repository
{
    private static Repository instance;

    private LiveData<ArrayList<Greenhouse>> greenhouses;

    private ArrayList<Measurement> measurementsArrList = new ArrayList<>();
    private MutableLiveData<ArrayList<Measurement>> latestMeasurements = new MutableLiveData<>();
    private MutableLiveData<Measurement> latestMeasurement = new MutableLiveData<>();

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

    public LiveData<Measurement> getLatestMeasurement()
    {
        latestMeasurement.setValue(latestMeasurements.getValue().get(0));
        return latestMeasurement;
    }

    public LiveData<ArrayList<Measurement>> getLatestMeasurements()
    {
        return this.latestMeasurements;
    }


    public void addDummyMeasurements()
    {
        Measurement mes1 = new Measurement("25.6", "56", "643", "2019-05-05T09:45:00Z");
        Measurement mes2 = new Measurement("26.1", "60", "640", "2019-05-05T09:35:00Z");
        Measurement mes3 = new Measurement("27.2", "62", "645", "2019-05-05T09:25:00Z");
        Measurement mes4 = new Measurement("26.4", "59", "660", "2019-05-05T09:15:00Z");
        Measurement mes5 = new Measurement("24.8", "55", "680", "2019-05-05T09:05:00Z");

        measurementsArrList.add(mes1);
        measurementsArrList.add(mes2);
        measurementsArrList.add(mes3);
        measurementsArrList.add(mes4);
        measurementsArrList.add(mes5);

        latestMeasurements.setValue(measurementsArrList);
    }
}