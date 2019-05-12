package com.example.app_v1.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.app_v1.apiclients.GemsApi;
import com.example.app_v1.apiclients.GemsApiClient;
import com.example.app_v1.models.Greenhouse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GreenhouseRepository
{
    private static final String TAG = "GreenhouseRepo";

    private List<Integer> tempList = new ArrayList<>();
    private MutableLiveData<List<Integer>> greenhouses = new MutableLiveData<>();
    private List<Greenhouse> returnedValues;

    private static GreenhouseRepository instance;

    public static GreenhouseRepository getInstance() {
        if (instance == null) {
            instance = new GreenhouseRepository();
        }

        return instance;
    }

    public void retrieveGreenhouses() {
        Retrofit retrofit = GemsApiClient.getRetrofitClient();
        GemsApi api = retrofit.create(GemsApi.class);
        Call call = api.getAllGreenhouses();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.body() != null) {
                    returnedValues = (List<Greenhouse>)response.body();

                    for( Greenhouse g : returnedValues) {
                        tempList.add(g.getId());
                    }
                }

                greenhouses.postValue(tempList);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.w(TAG, "onFailure: " + call.request().url().toString());
                tempList = new ArrayList<>();
            }
        });
    }

    public LiveData<List<Integer>> getGreenhouses() {
        return greenhouses;
    }

}
