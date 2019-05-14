package com.example.app_v1.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.models.Threshold;
import com.example.app_v1.models.ThresholdInterval;
import com.example.app_v1.repositories.MeasurementRepository;
import com.example.app_v1.repositories.ThresholdRepository;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivityViewModel extends AndroidViewModel
{
    private MeasurementRepository repo;

    private LiveData<ArrayList<Measurement>> latestMeasurementsFromRepo;
    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedGreenhouseId = new MutableLiveData<>();

    private LiveData<List<Threshold>> allThresholds;
    private ThresholdRepository thresholdRepository;


    public DashboardActivityViewModel(@NonNull Application application) {
        super(application);
        thresholdRepository = new ThresholdRepository(application);
        allThresholds = thresholdRepository.getAllThresholds();
    }

    public void initViewModel(int greenhouseId)
    {
        repo = MeasurementRepository.getInstance();
        selectedTabIndex.setValue(0);
        repo.startFetchingDataFromApi(greenhouseId);

    }
    public void sendThresholdToApi(int id, String measurement, ThresholdInterval thresholdInterval){thresholdRepository.sendThresholdToApi(id,measurement,thresholdInterval);}
    public LiveData<List<Threshold>> getAllThresholds() {
        return allThresholds;
    }

    public void update(Threshold threshold){
        thresholdRepository.update(threshold);
    }

    public void insert(Threshold threshold){
        thresholdRepository.insert(threshold);
    }

    public void delete(Threshold threshold){
        thresholdRepository.delete(threshold);
    }

    public void deleteAllThresholds(){
        thresholdRepository.deleteAllThresholds();
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
        return repo.extractTemperaturesFromMeasurements(measurements);
    }

    public ArrayList<Humidity> extractLatestHumidityFromMeasurements(ArrayList<Measurement> measurements)
    {
        repo = MeasurementRepository.getInstance();
        return repo.extractHumidityFromMeasurements(measurements);
    }

    public ArrayList<Co2> extractLatestCo2FromMeasurements(ArrayList<Measurement> measurements)
    {
        repo = MeasurementRepository.getInstance();
        return repo.extractCo2FromMeasurements(measurements);
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