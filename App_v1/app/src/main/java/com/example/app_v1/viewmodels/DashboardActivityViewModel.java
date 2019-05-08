package com.example.app_v1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.Repository;
import com.example.app_v1.utils.DTimeFormatHelper;

import java.text.ParseException;
import java.util.ArrayList;

public class DashboardActivityViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedGreenhouseId = new MutableLiveData<>();

    private ArrayList<Measurement> latestMeasurementsArrList = new ArrayList<>();
    private MutableLiveData<Measurement> latestMeasurementMld = new MutableLiveData<>();

    private ArrayList<Temperature> latestTemperaturesArrList = new ArrayList<>();
    private MutableLiveData<ArrayList<Temperature>> latestTemperatures = new MutableLiveData<>();

    private ArrayList<Humidity> latestHumidityArrList = new ArrayList<>();
    private MutableLiveData<ArrayList<Humidity>> latestHumiditys = new MutableLiveData<>();

    private ArrayList<Co2> latestCo2ArrList = new ArrayList<>();
    private MutableLiveData<ArrayList<Co2>> latestCo2s = new MutableLiveData<>();

    public void initViewModel()
    {
        repo = Repository.getInstance();
        selectedTabIndex.setValue(0);
        repo.addDummyMeasurements();
        repo.fetchLatestMeasurementsFromApi();
    }

    public void setSelectedTabIndex(Integer index)
    {
        selectedTabIndex.setValue(index);
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

    private ArrayList<Measurement> getLatestMeasurementsArrList()
    {
        latestMeasurementsArrList.clear();
        latestMeasurementsArrList = repo.getLatestMeasurements().getValue();

        return this.latestMeasurementsArrList;
    }

    public LiveData<Measurement> getLatestMeasurement()
    {
        latestMeasurementMld.setValue(getLatestMeasurementsArrList().get(0));

        return latestMeasurementMld;
    }

    public LiveData<ArrayList<Temperature>> getLatestTemperatures() throws ParseException
    {
        latestTemperaturesArrList.clear();
        latestMeasurementsArrList = repo.getLatestMeasurements().getValue();

        for(int i = 0; i < latestMeasurementsArrList.size(); i++)
        {
            Temperature temperature = new Temperature(latestMeasurementsArrList.get(i).getTemperature());
            temperature.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(latestMeasurementsArrList.get(i).getTimeStamp()));
            temperature.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(latestMeasurementsArrList.get(i).getTimeStamp()));

            latestTemperaturesArrList.add(temperature);
        }
        latestTemperatures.setValue(latestTemperaturesArrList);
        return latestTemperatures;
    }

    public LiveData<ArrayList<Humidity>> getLatestHumiditys() throws ParseException
    {
        latestHumidityArrList.clear();
        latestMeasurementsArrList = repo.getLatestMeasurements().getValue();

        for(int i = 0; i < latestMeasurementsArrList.size(); i++)
        {
            Humidity humidity = new Humidity(latestMeasurementsArrList.get(i).getHumidity());
            humidity.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(latestMeasurementsArrList.get(i).getTimeStamp()));
            humidity.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(latestMeasurementsArrList.get(i).getTimeStamp()));

            latestHumidityArrList.add(humidity);
        }

        latestHumiditys.setValue(latestHumidityArrList);
        return latestHumiditys;
    }

    public LiveData<ArrayList<Co2>> getLatestCo2s() throws ParseException
    {
        latestCo2ArrList.clear();
        latestMeasurementsArrList = repo.getLatestMeasurements().getValue();

        for(int i = 0; i < latestMeasurementsArrList.size(); i++)
        {
            Co2 co2 = new Co2(latestMeasurementsArrList.get(i).getcO2());
            co2.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(latestMeasurementsArrList.get(i).getTimeStamp()));
            co2.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(latestMeasurementsArrList.get(i).getTimeStamp()));

            latestCo2ArrList.add(co2);
        }

        latestCo2s.setValue(latestCo2ArrList);
        return latestCo2s;
    }
}
