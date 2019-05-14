package com.example.app_v1.repositories;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.example.app_v1.apiclients.GemsApi;
import com.example.app_v1.apiclients.GemsApiClient;
import com.example.app_v1.models.Threshold;
import com.example.app_v1.models.ThresholdInterval;
import com.example.app_v1.room.ThresholdDAO;
import com.example.app_v1.room.ThresholdDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public void sendThresholdToApi(int id, String measurement, ThresholdInterval thresholdInterval) {
        Retrofit retrofit = GemsApiClient.getRetrofitClient();
        GemsApi api = retrofit.create(GemsApi.class);
                Call call = api.setThreshold(id,measurement,thresholdInterval);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        response.body();
                        if (response.isSuccessful()){
                            Log.d("OnSuccess", "onResponse: " + response.toString());
                            assert response.body() != null;
                    }

                }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e("OnFailure", "Failure: " + t.getMessage() + " , StackTrace: " + t.getLocalizedMessage());
                    }


                });
    }

}
