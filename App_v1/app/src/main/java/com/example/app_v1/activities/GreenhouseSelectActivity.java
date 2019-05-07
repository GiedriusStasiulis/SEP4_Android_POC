package com.example.app_v1.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;

import com.example.app_v1.viewmodels.GreenhouseSelectActivityViewModel;

public class GreenhouseSelectActivity extends AppCompatActivity {
    private static final String TAG = "GreenhouseSelectActivity";

    public Spinner selectGreenhouse;
    public Button buttonGo;

    private GreenhouseSelectActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
