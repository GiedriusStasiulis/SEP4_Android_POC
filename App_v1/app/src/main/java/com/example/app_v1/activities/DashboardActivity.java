package com.example.app_v1.activities;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.app_v1.R;
import com.example.app_v1.adapters.SectionsPageAdapter;
import com.example.app_v1.fragments.MeasurementDetailsFragment;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.viewmodels.MeasurementDetailsActivityViewModel;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity
{
    private static final String TAG = "DashboardActivity";

    public Toolbar toolbar;
    public TabLayout tabLayout;
    private SectionsPageAdapter sectionsPageAdapter;
    public ViewPager viewPager;

    private MeasurementDetailsActivityViewModel measurementDetailsActivityViewModel;

    final int[] tabIcons = new int[]{R.drawable.tab_icon_temperature,R.drawable.tab_icon_humidity,R.drawable.tab_icon_co2};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Toolbar settings
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard - GH01");
        getSupportActionBar().setSubtitle("Updated: " + getResources().getString(R.string.value_last_updated));

        //Enable back-arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Init sections page adapter and setup with page viewer
        viewPager = findViewById(R.id.viewPager);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPageAdapter);
        setupViewPager(viewPager);

        viewPager.setOffscreenPageLimit(2);

        //Set up tab layout with view pager and add icons to tabs
        tabLayout = findViewById(R.id.measurementDetailsTabs);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);

        //Dummy tab titles
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("T: -.- \u2103");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("H: -.- %");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("--- ppm");

        measurementDetailsActivityViewModel = ViewModelProviders.of(this).get(MeasurementDetailsActivityViewModel.class);
        measurementDetailsActivityViewModel.initViewModel();

        measurementDetailsActivityViewModel.getLatestMeasurement().observe(this, new Observer<Measurement>() {
            @Override
            public void onChanged(@Nullable Measurement measurement)
            {
                Objects.requireNonNull(tabLayout.getTabAt(0)).setText("");
                Objects.requireNonNull(tabLayout.getTabAt(1)).setText("");
                Objects.requireNonNull(tabLayout.getTabAt(2)).setText("");

                Objects.requireNonNull(tabLayout.getTabAt(0)).setText(String.format("T: %s\u2103", measurement.getTemperature()));
                Objects.requireNonNull(tabLayout.getTabAt(1)).setText(String.format("H: %s%%", measurement.getHumidity()));
                Objects.requireNonNull(tabLayout.getTabAt(2)).setText(String.format("%s ppm", measurement.getcO2()));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                measurementDetailsActivityViewModel.setSelectedTabIndex(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MeasurementDetailsFragment());
        adapter.addFragment(new MeasurementDetailsFragment());
        adapter.addFragment(new MeasurementDetailsFragment());

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_settings:

                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
            Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
            Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);

            Objects.requireNonNull(tabLayout.getTabAt(0)).setText("T: 25.5 \u2103");
            Objects.requireNonNull(tabLayout.getTabAt(1)).setText("H: 35 %");
            Objects.requireNonNull(tabLayout.getTabAt(2)).setText("650 ppm");
        }

        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            tabLayout.getTabAt(0).setIcon(null);
            tabLayout.getTabAt(1).setIcon(null);
            tabLayout.getTabAt(2).setIcon(null);

            Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Temperature: 25.5 \u2103");
            Objects.requireNonNull(tabLayout.getTabAt(1)).setText("Humidity: 35 %");
            Objects.requireNonNull(tabLayout.getTabAt(2)).setText("CO2: 650 ppm");
        }
    }
}
