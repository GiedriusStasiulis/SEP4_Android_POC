package com.example.app_v1.viewmodels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.app_v1.repositories.GreenhouseRepository;
import java.util.List;

public class GreenhouseSelectActivityViewModel extends ViewModel {
    private static final String TAG = "GhSelectViewModel";
    private GreenhouseRepository repository = GreenhouseRepository.getInstance();

    public void init() {
        Log.d(TAG, "init: called");
        repository.retrieveGreenhouses();
    }

    public LiveData<List<Integer>> getGreenhouses() {
        return repository.getGreenhouses();
    }



}