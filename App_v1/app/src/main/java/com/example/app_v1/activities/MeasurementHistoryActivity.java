package com.example.app_v1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.app_v1.R;
import com.example.app_v1.adapters.SectionsPageAdapter;
import com.example.app_v1.fragments.MeasurementHistoryFragment;
import com.example.app_v1.viewmodels.MeasurementHistoryViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MeasurementHistoryActivity extends AppCompatActivity
{
    private static final String TAG = "MeasurementHistoryActivity";

    private Toolbar toolbar;
    private TabLayout measurementSelectTabLayout;
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager measurementHistoryViewPager;

    private String measurementTypeFromBundle;
    private int selectedGreenhouseId;

    private MeasurementHistoryViewModel measurementHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_history);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Enable back-arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Init sections page adapter and setup with page viewer
        measurementHistoryViewPager = findViewById(R.id.measurementHistoryViewPager);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        measurementHistoryViewPager.setAdapter(sectionsPageAdapter);
        setupViewPager(measurementHistoryViewPager);

        measurementHistoryViewPager.setOffscreenPageLimit(2);

        measurementSelectTabLayout = findViewById(R.id.measurementSelectTabLayout);
        measurementSelectTabLayout.setupWithViewPager(measurementHistoryViewPager);

        Objects.requireNonNull(measurementSelectTabLayout.getTabAt(0)).setText("Temperature");
        Objects.requireNonNull(measurementSelectTabLayout.getTabAt(1)).setText("Humidity");
        Objects.requireNonNull(measurementSelectTabLayout.getTabAt(2)).setText("Co2");

        measurementHistoryViewModel = ViewModelProviders.of(this).get(MeasurementHistoryViewModel.class);
        measurementHistoryViewModel.initViewModel();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            measurementTypeFromBundle = bundle.getString("measurement_type");
            selectedGreenhouseId = bundle.getInt("selectedGreenhouseId");

            measurementHistoryViewModel.setSelectedGreenhouseId(selectedGreenhouseId);
        }

        switch (measurementTypeFromBundle)
        {
            case "Temperature":

                measurementSelectTabLayout.getTabAt(0).select();
                measurementHistoryViewModel.setSelectedTabIndex(0);
                break;

            case "Humidity":

                measurementSelectTabLayout.getTabAt(1).select();
                measurementHistoryViewModel.setSelectedTabIndex(1);
                break;

            case "Co2":

                measurementSelectTabLayout.getTabAt(2).select();
                measurementHistoryViewModel.setSelectedTabIndex(2);
                break;

            default:
                break;
        }

        measurementSelectTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                measurementHistoryViewModel.setSelectedTabIndex(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new MeasurementHistoryFragment());
        adapter.addFragment(new MeasurementHistoryFragment());
        adapter.addFragment(new MeasurementHistoryFragment());

        viewPager.setAdapter(adapter);
    }
}