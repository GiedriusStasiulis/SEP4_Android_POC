package com.example.app_v1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.Repository;
import java.text.ParseException;
import java.util.ArrayList;

public class MeasurementHistoryViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();
    private ArrayList<Temperature> temperaturesInDateRange = new ArrayList<>();
    private MutableLiveData<ArrayList<Temperature>> temperaturesInRangeMld = new MutableLiveData<>();
    private ArrayList<Humidity> humidityInDateRange = new ArrayList<>();
    private ArrayList<Co2> co2InDateRange = new ArrayList<>();

    public void initViewModel()
    {
        repo = Repository.getInstance();
        selectedTabIndex.setValue(0);
    }

    public void setSelectedTabIndex(Integer index)
    {
        selectedTabIndex.postValue(index);
    }

    public MutableLiveData<Integer> getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public MutableLiveData<ArrayList<Temperature>> getTemperaturesInDateRange(String dateTimeFrom, String dateTimeTo) throws ParseException
    {
        temperaturesInDateRange.clear();
        temperaturesInDateRange = repo.getTemperaturesInDateRange(dateTimeFrom,dateTimeTo);

        temperaturesInRangeMld.setValue(temperaturesInDateRange);

        return this.temperaturesInRangeMld;
    }

    public ArrayList<Humidity> getHumidityInDateRange(String dateTimeFrom, String dateTimeTo) throws ParseException
    {
        humidityInDateRange.clear();
        humidityInDateRange = repo.getHumidityInDateRange(dateTimeFrom,dateTimeTo);

        return this.humidityInDateRange;
    }

    public ArrayList<Co2> getCo2InDateRange(String dateTimeFrom, String dateTimeTo) throws ParseException
    {
        co2InDateRange.clear();
        co2InDateRange = repo.getCo2InDateRange(dateTimeFrom,dateTimeTo);

        return this.co2InDateRange;
    }
}
