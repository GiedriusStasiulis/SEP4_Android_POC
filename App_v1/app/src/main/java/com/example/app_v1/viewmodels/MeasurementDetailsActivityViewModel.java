package com.example.app_v1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.Repository;

import java.util.ArrayList;

public class MeasurementDetailsActivityViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<Integer>();

    private LiveData<Temperature> latestTemperature;
    private LiveData<Humidity> latestHumidity;
    private LiveData<Co2> latestCo2;
    private LiveData<ArrayList<Temperature>> temperatureDataInRange;
    private LiveData<ArrayList<Humidity>> humidityDataInRange;
    private LiveData<ArrayList<Co2>> co2DataInRange;

    public void initViewModel()
    {
        repo = Repository.getInstance();
        selectedTabIndex.setValue(0);
    }

    public void setSelectedTabIndex(Integer index)
    {
        selectedTabIndex.setValue(index);
    }

    public MutableLiveData<Integer> getSelectedTabIndex()
    {
        return selectedTabIndex;
    }
}
