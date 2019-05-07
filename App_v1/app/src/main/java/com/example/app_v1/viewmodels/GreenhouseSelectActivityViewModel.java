package com.example.app_v1.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.repositories.Repository;

import java.util.ArrayList;
import java.util.List;

public class GreenhouseSelectActivityViewModel extends ViewModel {
    private static final String TAG = "GhSelectViewModel";

    private Repository repo;
    private List<Integer> greenhouses = new ArrayList<>();

    public void init() {
        repo = Repository.getInstance();

        repo.addDummyGreenhouses();
    }

    public List<Integer> getGreenhouses() {


        try {
            List<Greenhouse> returnedValues = repo.getGreenhouses().getValue();
            for( Greenhouse g : returnedValues) {
                greenhouses.add(g.getId());
            }

            return greenhouses;

        } catch (NullPointerException e) {
            Log.d(TAG, "getGreenhouses: " + e.toString());
            return null;
        }

    }

}