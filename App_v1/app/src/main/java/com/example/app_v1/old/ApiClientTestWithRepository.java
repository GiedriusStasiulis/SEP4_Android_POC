package com.example.app_v1.old;

import androidx.lifecycle.MutableLiveData;

import com.example.app_v1.models.Temperature;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ApiClientTestWithRepository
{
    private static ApiClientTestWithRepository instance;

    private Temperature tempObj;

    final MutableLiveData<Temperature> data = new MutableLiveData<>();

    static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

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
        /*
        final IAndroidWebApiClient apiClient = AndroidWebApiClient.getRetrofitClient().create(IAndroidWebApiClient.class);

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run()
            {
                Call<Temperature> call = apiClient.measurements();

                call.enqueue(new Callback<Temperature>() {
                    @Override
                    public void onResponse(Call<Temperature> call, Response<Temperature> response) {

                        response.body();

                        if(response.isSuccessful())
                        {
                            Log.d("OnSuccess", "onResponse: " + response.toString());

                            assert response.body() != null;
                            String temp = response.body().getTemperature();
                            String dTime = response.body().getDate();

                            tempObj = new Temperature(temp,dTime);

                            Log.d("OnSuccess", "onResponse: " + tempObj.toString());

                            data.setValue(tempObj);
                        }
                    }

                    @Override
                    public void onFailure(Call<Temperature> call, Throwable t) {
                        Log.e("OnFailure", "Failure: Sum ting wong: "  +  t.getMessage() + " , StackTrace: " + t.getLocalizedMessage());
                    }
                });
            }
        },0,5, TimeUnit.SECONDS);
         */
        return data;
    }
}
