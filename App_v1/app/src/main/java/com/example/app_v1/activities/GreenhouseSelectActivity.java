package com.example.app_v1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.app_v1.R;
import com.example.app_v1.viewmodels.GreenhouseSelectActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class GreenhouseSelectActivity extends AppCompatActivity {
    private static final String TAG = "GhSelectActivity";

    public Spinner selectGreenhouse;
    public Button buttonGo;
    private List<Integer> greenhouses = new ArrayList<>();
    private int selectedGreenhouseId;
    private ArrayAdapter<Integer> adapter;

    private GreenhouseSelectActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greenhouse_select);

        selectGreenhouse = findViewById(R.id.spinner_select_greenhouse);
        buttonGo = findViewById(R.id.button_go);


        viewModel = ViewModelProviders.of(this).get(GreenhouseSelectActivityViewModel.class);
        viewModel.init();

        Log.d(TAG, "onCreate: called");

        // populate dropdown menu

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, greenhouses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGreenhouse.setAdapter(adapter);

        selectGreenhouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = selectGreenhouse.getSelectedItem().toString();
                Log.d(TAG, "spinner selection: " + selection);

                selectedGreenhouseId = Integer.parseInt(selection);
                buttonGo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                buttonGo.setVisibility(View.GONE);
            }
        });

        viewModel.getGreenhouses().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                greenhouses = integers;
                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(GreenhouseSelectActivity.this, android.R.layout.simple_spinner_item, greenhouses);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectGreenhouse.setAdapter(adapter);

            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GreenhouseSelectActivity.this, DashboardActivity.class);
                intent.putExtra("selectedGreenhouseId", selectedGreenhouseId);
                startActivity(intent);
            }
        });

    }
}
