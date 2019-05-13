package com.example.app_v1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.MeasurementRepository;

import java.util.ArrayList;

public class MeasurementHistoryViewModel extends ViewModel
{
    private MeasurementRepository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedGreenhouseId = new MutableLiveData<>();

    private LiveData<ArrayList<Temperature>> temperaturesInDateRangeFromRepo;
    private LiveData<ArrayList<Humidity>> humidityInDateRangeFromRepo;
    private LiveData<ArrayList<Co2>> co2InDateRangeFromRepo;

    public void initViewModel()
    {
        repo = MeasurementRepository.getInstance();
        selectedTabIndex.setValue(0);
        selectedGreenhouseId.setValue(0);
    }

    public Boolean validateSearchParameters(String dateRangeString, String timeFrom, String timeTo)
    {
        boolean isValid;

        isValid = !dateRangeString.equals("Select") && !timeFrom.equals("Select") && !timeTo.equals("Select");

        return isValid;
    }

    public void setSelectedTabIndex(Integer index)
    {
        selectedTabIndex.postValue(index);
    }

    public MutableLiveData<Integer> getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedGreenhouseId(Integer id)
    {
        selectedGreenhouseId.setValue(id);
    }

    public MutableLiveData<Integer> getSelectedGreenhouseId()
    {
        return this.selectedGreenhouseId;
    }

    public LiveData<ArrayList<Temperature>> getTemperaturesInDateRange(int greenhouseId, String dateTimeFrom, String dateTimeTo)
    {
        repo = MeasurementRepository.getInstance();
        temperaturesInDateRangeFromRepo = repo.getTemperaturesInDateRange(greenhouseId,dateTimeFrom,dateTimeTo);

        return this.temperaturesInDateRangeFromRepo;
    }

    public LiveData<ArrayList<Humidity>> getHumidityInDateRange(int greenhouseId, String dateTimeFrom, String dateTimeTo)
    {
        repo = MeasurementRepository.getInstance();
        humidityInDateRangeFromRepo = repo.getHumidityInDateRange(greenhouseId,dateTimeFrom,dateTimeTo);

        return this.humidityInDateRangeFromRepo;
    }

    public LiveData<ArrayList<Co2>> getCo2InDateRange(int greenhouseId, String dateTimeFrom, String dateTimeTo)
    {
        repo = MeasurementRepository.getInstance();
        co2InDateRangeFromRepo = repo.getCo2InDateRange(greenhouseId,dateTimeFrom,dateTimeTo);

        return this.co2InDateRangeFromRepo;
    }
}