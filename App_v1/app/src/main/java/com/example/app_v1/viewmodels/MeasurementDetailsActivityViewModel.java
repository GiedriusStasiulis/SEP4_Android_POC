package com.example.app_v1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.Repository;
import com.example.app_v1.utils.DTimeFormatHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MeasurementDetailsActivityViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();

    private ArrayList<Measurement> latestMeasurements = new ArrayList<>();
    private ArrayList<Temperature> latestTemperatures = new ArrayList<>();
    private MutableLiveData<ArrayList<Temperature>> latestTemperaturesLd = new MutableLiveData<>();

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

    public LiveData<ArrayList<Temperature>> getLatestTemperatures() throws ParseException
    {
        latestMeasurements.clear();
        latestMeasurements = repo.getLatestMeasurements().getValue();

        for(int i = 0; i < latestMeasurements.size(); i++)
        {
            Temperature temperature = new Temperature(latestMeasurements.get(i).getTemperature());
            String time = DTimeFormatHelper.getTimeFromTimestamp(latestMeasurements.get(i).getTimeStamp());
            String date = DTimeFormatHelper.getDateFromTimestamp(latestMeasurements.get(i).getTimeStamp());
            temperature.setTime(time);
            temperature.setDate(date);

            latestTemperatures.add(temperature);
        }
        latestTemperaturesLd.setValue(latestTemperatures);
        return latestTemperaturesLd;
    }
}
