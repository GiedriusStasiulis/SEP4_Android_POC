package com.example.app_v1.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.models.Threshold;
import com.example.app_v1.repositories.MeasurementRepository;
import com.example.app_v1.repositories.ThresholdRepository;
import com.example.app_v1.utils.DateTimeConverterHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashboardActivityViewModel extends ViewModel
{
    private MeasurementRepository repo;

    private LiveData<ArrayList<Measurement>> latestMeasurementsFromRepo;
    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedGreenhouseId = new MutableLiveData<>();
    private ThresholdRepository thresholdRepository;
    private Context context;

    public void initViewModel(int greenhouseId, Context _context)
    {
        repo = MeasurementRepository.getInstance();
        selectedTabIndex.setValue(0);
        repo.startFetchingDataFromApi(greenhouseId);
        context = _context;
        thresholdRepository = new ThresholdRepository(context);
    }
    public LiveData<List<Threshold>> getAllThresholds() {
        return thresholdRepository.getAllThresholds();
    }

    public LiveData<ArrayList<Measurement>> getLatestMeasurementsFromRepo()
    {
        if(latestMeasurementsFromRepo == null)
        {
            repo = MeasurementRepository.getInstance();
            latestMeasurementsFromRepo = repo.getLatestMeasurementsFromApi();
        }
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
                temperature.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(measurements.get(i).getTimeStamp()));
                temperature.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(measurements.get(i).getTimeStamp()));

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
                humidity.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(measurements.get(i).getTimeStamp()));
                humidity.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(measurements.get(i).getTimeStamp()));

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
                co2.setTime(DateTimeConverterHelper.convertTimestampISO8601ToTimeString(measurements.get(i).getTimeStamp()));
                co2.setDate(DateTimeConverterHelper.convertTimestampISO8601ToDateString(measurements.get(i).getTimeStamp()));

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

    public LiveData<Integer> getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedGreenhouseId(Integer id)
    {
        selectedGreenhouseId.setValue(id);
    }

    public LiveData<Integer> getSelectedGreenhouseId()
    {
        return this.selectedGreenhouseId;
    }

    public void stopRepoRunnable()
    {
        repo.stopFetchingDataFromApi();
    }
}