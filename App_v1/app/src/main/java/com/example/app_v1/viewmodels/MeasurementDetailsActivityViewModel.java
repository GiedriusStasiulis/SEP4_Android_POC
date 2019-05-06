package com.example.app_v1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.Repository;

import java.util.ArrayList;

public class MeasurementDetailsActivityViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();

    private ArrayList<Measurement> latestMeasurements = new ArrayList<>();

    private ArrayList<Temperature> latestTemperatures = new ArrayList<>();
    private MutableLiveData<ArrayList<Temperature>> latestTemperaturesLd = new MutableLiveData<>();

    private LiveData<Humidity> latestHumidity;
    private LiveData<Co2> latestCo2;
    private LiveData<ArrayList<Temperature>> temperatureDataInRange;
    private LiveData<ArrayList<Humidity>> humidityDataInRange;
    private LiveData<ArrayList<Co2>> co2DataInRange;

    public void initViewModel()
    {
        repo = Repository.getInstance();
        selectedTabIndex.setValue(0);
        repo.addDummyMeasurements();
    }

    public void setSelectedTabIndex(Integer index)
    {
        selectedTabIndex.setValue(index);
    }

    public MutableLiveData<Integer> getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public LiveData<ArrayList<Temperature>> getLatestTemperatures()
    {
        latestMeasurements.clear();
        latestMeasurements = repo.getMeasurements().getValue();

        for(int i = 0; i < latestMeasurements.size(); i++)
        {
            Temperature temperature = new Temperature(latestMeasurements.get(i).getTemperature(),latestMeasurements.get(i).getTimeStamp());
            latestTemperatures.add(temperature);
        }

        latestTemperaturesLd.setValue(latestTemperatures);
        return latestTemperaturesLd;
    }
}
