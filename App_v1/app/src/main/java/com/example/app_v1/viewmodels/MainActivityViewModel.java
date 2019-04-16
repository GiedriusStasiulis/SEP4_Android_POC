package com.example.app_v1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.app_v1.models.Temperature;

public class MainActivityViewModel extends ViewModel
{
    private LiveData<Temperature> ldTemperature;

    public void init()
    {

    }

    public LiveData<Temperature> getLatestTemperature()
    {
        return this.ldTemperature;
    }
}
