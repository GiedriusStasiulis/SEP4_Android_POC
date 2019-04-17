package com.example.app_v1.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.example.app_v1.apiclients.AndroidWebApiClient;
import com.example.app_v1.apiclients.IAndroidWebApiClient;
import com.example.app_v1.models.Temperature;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiClientTestWithRepository
{
    private static ApiClientTestWithRepository instance;

    private Temperature tempObj;

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
        IAndroidWebApiClient apiClient = AndroidWebApiClient.getRetrofitClient().create(IAndroidWebApiClient.class);
        Call<Temperature> call = apiClient.values();

        call.enqueue(new Callback<Temperature>() {
            @Override
            public void onResponse(Call<Temperature> call, Response<Temperature> response) {

                response.body();
/*
                Log.d("OnSuccess", "onResponse: " + response.toString());
                Log.d("OnSuccess", "onResponse: Received information: " + response.body().toString());

                Log.d("OnSuccess", "onResponse: Code: " + response.code());
*/
                if(response != null && response.isSuccessful())
                {
                    Log.d("OnSuccess", "onResponse: " + response.toString());

                    String temp = response.body().getTemperature();
                    String dTime = response.body().getDateTime();

                    tempObj = new Temperature(temp,dTime);

                    Log.d("OnSuccess", "onResponse: " + tempObj.toString());

                    data.setValue(tempObj);
                }

                if(response == null)
                {
                    Log.d("OnFail", "Response json is empty");
                }
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                Log.e("OnFailure", "Failure: Sum ting wong: "  +  t.getMessage() + " , StackTrace: " + t.getLocalizedMessage());
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