package com.example.app_v1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.Repository;
import com.example.app_v1.utils.DTimeFormatHelper;
import java.text.ParseException;
import java.util.ArrayList;

public class MeasurementDetailsActivityViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();

    private ArrayList<Measurement> latestMeasurements = new ArrayList<>();

    private ArrayList<Temperature> latestTemperaturesArrList = new ArrayList<>();
    private MutableLiveData<ArrayList<Temperature>> latestTemperatures = new MutableLiveData<>();
    private MutableLiveData<Temperature> latestTemperature = new MutableLiveData<>();

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

    public LiveData<Temperature> getLatestTemperature()
    {
        latestTemperature.setValue(latestTemperatures.getValue().get(0));
        return latestTemperature;
    }

    public LiveData<ArrayList<Temperature>> getLatestTemperatures() throws ParseException
    {
        latestMeasurements.clear();
        latestMeasurements = repo.getLatestMeasurements().getValue();

        for(int i = 0; i < latestMeasurements.size(); i++)
        {
            Temperature temperature = new Temperature(latestMeasurements.get(i).getTemperature());
            String time = DTimeFormatHelper.getTimeFromISO8601Timestamp(latestMeasurements.get(i).getTimeStamp());
            String date = DTimeFormatHelper.getDateFromISO8601Timestamp(latestMeasurements.get(i).getTimeStamp());
            temperature.setTime(time);
            temperature.setDate(date);

            latestTemperaturesArrList.add(temperature);
        }
        latestTemperatures.setValue(latestTemperaturesArrList);
        return latestTemperatures;
    }
}
