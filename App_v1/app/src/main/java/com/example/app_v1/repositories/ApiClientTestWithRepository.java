package com.example.app_v1.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.nfc.Tag;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.example.app_v1.MainActivity;
import com.example.app_v1.R;
import com.example.app_v1.apiclients.AndroidWebApiClient;
import com.example.app_v1.apiclients.IAndroidWebApiClient;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.utils.Utils;

import org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
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