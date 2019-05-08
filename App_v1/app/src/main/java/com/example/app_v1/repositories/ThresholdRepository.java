package com.example.app_v1.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.app_v1.models.Threshold;

import java.util.List;

public class ThresholdRepository {
    private ThresholdDAO thresholdDAO;
    private LiveData<List<Threshold>> allThresholds;

    public ThresholdRepository(Context context){
        ThresholdDatabase database = ThresholdDatabase.getInstance(context);
        thresholdDAO = database.thresholdsDAO();
        allThresholds = thresholdDAO.getAllThresholds();
    }

    public void insert(Threshold threshold){
        new InsertThresholdAsyncTask(thresholdDAO).execute(threshold);

    }
    public void update(Threshold threshold){

        new UpdateThresholdAsyncTask(thresholdDAO).execute(threshold);
    }
    public void delete(Threshold threshold){
        new DeleteThresholdAsyncTask(thresholdDAO).execute(threshold);
    }
    public void deleteAllThresholds(){
        new DeleteAllThresholdsAsyncTask(thresholdDAO).execute();
    }

    public LiveData<List<Threshold>> getAllThresholds() {
        return allThresholds;
    }
    public static class  InsertThresholdAsyncTask extends AsyncTask<Threshold, Void, Void>{
        private ThresholdDAO thresholdDAO;

        private InsertThresholdAsyncTask(ThresholdDAO thresholdDAO){
            this.thresholdDAO = thresholdDAO;
        }

        @Override
        protected Void doInBackground(Threshold... thresholds)
        {
            thresholdDAO.insert(thresholds[0]);
            return null;
        }
    }

    public static class  UpdateThresholdAsyncTask extends AsyncTask<Threshold, Void, Void>{
        private ThresholdDAO thresholdDAO;

        private UpdateThresholdAsyncTask(ThresholdDAO thresholdDAO){
            this.thresholdDAO = thresholdDAO;
        }

        @Override
        protected Void doInBackground(Threshold... thresholds)
        {
            thresholdDAO.update(thresholds[0]);
            return null;
        }
    }

    public static class  DeleteThresholdAsyncTask extends AsyncTask<Threshold, Void, Void>{
        private ThresholdDAO thresholdDAO;

        private DeleteThresholdAsyncTask(ThresholdDAO thresholdDAO){
            this.thresholdDAO = thresholdDAO;
        }

        @Override
        protected Void doInBackground(Threshold... thresholds)
        {
            thresholdDAO.delete(thresholds[0]);
            return null;
        }
    }

    public static class  DeleteAllThresholdsAsyncTask extends AsyncTask<Void, Void, Void>{
        private ThresholdDAO thresholdDAO;

        private DeleteAllThresholdsAsyncTask(ThresholdDAO thresholdDAO){
            this.thresholdDAO = thresholdDAO;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            thresholdDAO.deleteAllThresholds();
            return null;
        }
    }
}
