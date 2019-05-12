package com.example.app_v1.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.app_v1.R;
import com.example.app_v1.viewmodels.GreenhouseSelectActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GreenhouseSelectActivity extends AppCompatActivity {
    private static final String TAG = "GhSelectActivity";

    public Spinner selectGreenhouse;
    private Toolbar toolbar2;
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
        toolbar2 = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar2);
        Objects.requireNonNull(getSupportActionBar()).setTitle("GMS");

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_logout_only,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_logout) {
            Intent logoutIntent = new Intent(GreenhouseSelectActivity.this, LogInActivity.class);
            logoutIntent.putExtra("finish", true);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logoutIntent);
            FirebaseAuth.getInstance().signOut();
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
