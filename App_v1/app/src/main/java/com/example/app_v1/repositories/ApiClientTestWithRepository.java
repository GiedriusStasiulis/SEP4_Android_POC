package com.example.app_v1.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.app_v1.apiclients.IAndroidWebApiClient;
import com.example.app_v1.models.Temperature;

import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientTestWithRepository
{
    private static ApiClientTestWithRepository instance;

    private Temperature tempObj;

    private static final String BASE_URL = "https://192.168.1.98:44398/";

    final MutableLiveData<Temperature> data = new MutableLiveData<>();

    public static ApiClientTestWithRepository getInstance()
    {
        if(instance == null)
        {
            instance = new ApiClientTestWithRepository();
        }
        return instance;
    }

    public MutableLiveData<Temperature> getLastTemperature()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IAndroidWebApiClient apiClient = retrofit.create(IAndroidWebApiClient.class);
        Call<Temperature> call = apiClient.getData();

        call.enqueue(new Callback<Temperature>() {
            @Override
            public void onResponse(Call<Temperature> call, Response<Temperature> response) {
                Log.d("OnSuccess", "onResponse: Server response: " + response.toString());
                Log.d("OnSuccess", "onResponse: Received information: " + response.body().toString());

                Float temp = response.body().getTemperature();
                Timestamp lUpdated = response.body().getDateTime();

                tempObj = new Temperature(temp,lUpdated);
                data.setValue(tempObj);
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                Log.e("OnFailure", "Failure: Sum ting wong: "  +  t.getMessage());
            }
        });

        //Hard coded values that work fine
        /*
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
        String time = "2019-04-14T12:00:00";
        Timestamp tDate = null;

        try {
            Date date = format.parse(time);
            tDate = new Timestamp(date.getTime());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        Temperature testTemp = new Temperature(25.6F, tDate);

        MutableLiveData<Temperature> data = new MutableLiveData<>();
        data.setValue(testTemp);
        */

        return data;
    }
}