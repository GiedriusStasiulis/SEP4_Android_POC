package com.example.app_v1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_v1.apiclients.GemsApi;
import com.example.app_v1.apiclients.GemsApiClient;
import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.repositories.Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GreenhouseSelectActivityViewModel extends ViewModel {
    private static final String TAG = "GhSelectViewModel";

    private List<Integer> temporaryList = new ArrayList<>();
    private MutableLiveData<List<Integer>> greenhouses = new MutableLiveData<>();
    private List<Greenhouse> returnedValues;
    private Repository repo;

    public void init() {
        repo = Repository.getInstance();
        repo.addDummyGreenhouses();

        queryGreenhouses();

    }

    public void queryGreenhouses() {
        Retrofit retrofit = GemsApiClient.getRetrofitClient();
        GemsApi api = retrofit.create(GemsApi.class);
        Call call = api.getAllGreenhouses();

        // sync method with dummy data from repo
        //returnedValues = repo.getTemporaryList().getValue();


        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.body() != null) {
                    returnedValues = (List<Greenhouse>)response.body();

                    for( Greenhouse g : returnedValues) {
                        temporaryList.add(g.getId());
                    }
                }

                greenhouses.postValue(temporaryList);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.w(TAG, "onFailure: " + call.request().url().toString());
                temporaryList = new ArrayList<>();
            }
        });

    }

    public LiveData<List<Integer>> getGreenhouses() {
        return greenhouses;
    }



}