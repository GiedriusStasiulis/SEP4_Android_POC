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

    public ArrayList<Temperature> extractLatestTemperaturesFromMeasurements(ArrayList<Measurement> measurements)
    {
        repo = MeasurementRepository.getInstance();
        return repo.extractLatestTemperaturesFromMeasurements(measurements);
    }

    public ArrayList<Humidity> extractLatestHumidityFromMeasurements(ArrayList<Measurement> measurements)
    {
        repo = MeasurementRepository.getInstance();
        return repo.extractLatestHumidityFromMeasurements(measurements);
    }

    public ArrayList<Co2> extractLatestCo2FromMeasurements(ArrayList<Measurement> measurements)
    {
        repo = MeasurementRepository.getInstance();
        return repo.extractLatestCo2FromMeasurements(measurements);
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