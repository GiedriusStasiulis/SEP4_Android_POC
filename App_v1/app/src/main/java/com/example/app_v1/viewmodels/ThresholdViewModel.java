package com.example.app_v1.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.app_v1.models.Threshold;
import com.example.app_v1.repositories.ThresholdRepository;

import java.util.List;

public class ThresholdViewModel extends AndroidViewModel {
    private ThresholdRepository repository;
    private LiveData<List<Threshold>> allThresholds;
    public ThresholdViewModel(@NonNull Application application) {
        super(application);
        repository = new ThresholdRepository(application);
        allThresholds = repository.getAllThresholds();
    }

    public void insert(Threshold threshold){
        repository.insert(threshold);
    }
    public void update(Threshold threshold){
        repository.update(threshold);
    }
    public void delete(Threshold threshold){
        repository.delete(threshold);
    }
    public void deleteAllThresholds(){
        repository.deleteAllThresholds();
    }

    public LiveData<List<Threshold>> getAllThresholds() {
        return allThresholds;
    }
}
