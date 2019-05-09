package com.example.app_v1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.MeasurementRepository;
import com.example.app_v1.utils.DTimeFormatHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class DashboardActivityViewModel extends ViewModel
{
    private MeasurementRepository repo;

    private LiveData<ArrayList<Measurement>> latestMeasurementsFromRepo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedGreenhouseId = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void initViewModel(int greenhouseId)
    {
        repo = MeasurementRepository.getInstance();
        selectedTabIndex.setValue(0);
        repo.startFetchingDataFromApi(greenhouseId);
    }

    public LiveData<ArrayList<Measurement>> getLatestMeasurementsFromRepo()
    {
        isLoading.setValue(true);

        if(latestMeasurementsFromRepo == null)
        {
            Log.d("OnSuccess", "ltsMesr = null");
            repo = MeasurementRepository.getInstance();
            latestMeasurementsFromRepo = repo.getLatestMeasurementsFromApi();
        }

        isLoading.setValue(false);

        return this.latestMeasurementsFromRepo;
    }

    public ArrayList<Temperature> extractLatestTemperaturesFromMeasurements(ArrayList<Measurement> measurements) throws ParseException
    {
        ArrayList<Temperature> latestTemperaturesArrList;

        if(!measurements.isEmpty())
        {
            latestTemperaturesArrList = new ArrayList<>();

            for (int i = 0; i < measurements.size(); i++)
            {
                Temperature temperature = new Temperature(String.valueOf(measurements.get(i).getTemperature()));
                temperature.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurements.get(i).getTimeStamp()));
                temperature.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurements.get(i).getTimeStamp()));

                latestTemperaturesArrList.add(temperature);
            }
        }

        else
        {
            latestTemperaturesArrList = new ArrayList<>();
        }

        return latestTemperaturesArrList;
    }

    public ArrayList<Humidity> extractLatestHumidityFromMeasurements(ArrayList<Measurement> measurements) throws ParseException
    {
        ArrayList<Humidity> latestHumidityArrList;

        if(!measurements.isEmpty())
        {
            latestHumidityArrList = new ArrayList<>();

            for (int i = 0; i < measurements.size(); i++)
            {
                Humidity humidity = new Humidity(String.valueOf(measurements.get(i).getHumidity()));
                humidity.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurements.get(i).getTimeStamp()));
                humidity.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurements.get(i).getTimeStamp()));

                latestHumidityArrList.add(humidity);
            }
        }

        else
        {
            latestHumidityArrList = new ArrayList<>();
        }

        return latestHumidityArrList;
    }

    public ArrayList<Co2> extractLatestCo2FromMeasurements(ArrayList<Measurement> measurements) throws ParseException
    {
        ArrayList<Co2> latestCo2ArrList;

        if(!measurements.isEmpty())
        {
            latestCo2ArrList = new ArrayList<>();

            for (int i = 0; i < measurements.size(); i++)
            {
                Co2 co2 = new Co2(String.format(Locale.ENGLISH,"%.0f",measurements.get(i).getcO2()));
                co2.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurements.get(i).getTimeStamp()));
                co2.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurements.get(i).getTimeStamp()));

                latestCo2ArrList.add(co2);
            }
        }

        else
        {
            latestCo2ArrList = new ArrayList<>();
        }

        return latestCo2ArrList;
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

    public LiveData<Boolean> getIsLoading()
    {
        return this.isLoading;
    }

    public void stopRepoRunnable()
    {
        repo.stopFetchingDataFromApi();
    }
}