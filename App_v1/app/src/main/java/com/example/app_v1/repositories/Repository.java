package com.example.app_v1.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Measurement;
import java.util.ArrayList;

public class Repository
{
    private static Repository instance;

    private MutableLiveData<ArrayList<Greenhouse>> greenhouses = new MutableLiveData<>();
    private ArrayList<Greenhouse> greenhouseArrayList = new ArrayList<>();

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

    public LiveData<ArrayList<Measurement>> getLatestMeasurements()
    {
        return this.latestMeasurements;
    }

    public void addDummyMeasurements()
    {
        Measurement mes1 = new Measurement("26.2", "56", "672", "2019-05-05T09:45:00Z");
        Measurement mes2 = new Measurement("24.1", "60", "655", "2019-05-05T09:35:00Z");
        Measurement mes3 = new Measurement("27.2", "57", "675", "2019-05-05T09:25:00Z");
        Measurement mes4 = new Measurement("26.4", "59", "660", "2019-05-05T09:15:00Z");
        Measurement mes5 = new Measurement("24.8", "55", "680", "2019-05-05T09:05:00Z");

        measurementsArrList.add(mes1);
        measurementsArrList.add(mes2);
        measurementsArrList.add(mes3);
        measurementsArrList.add(mes4);
        measurementsArrList.add(mes5);

        latestMeasurements.setValue(measurementsArrList);
    }

    public void addDummyGreenhouses() {
        greenhouseArrayList.add(new Greenhouse(1, "Denmark", "Horsens", "8700", "Kattesund 12A"));
        greenhouseArrayList.add(new Greenhouse(2, "Denmark", "Horsens", "8700", "Kattesund 12A"));
        greenhouseArrayList.add(new Greenhouse(3, "Denmark", "Horsens", "8700", "Kattesund 12A"));
        greenhouseArrayList.add(new Greenhouse(4, "Denmark", "Horsens", "8700", "Kattesund 12A"));

        greenhouses.setValue(greenhouseArrayList);
    }
}