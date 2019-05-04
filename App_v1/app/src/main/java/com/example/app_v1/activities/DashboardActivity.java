package com.example.app_v1.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.app_v1.R;
import com.example.app_v1.adapters.SectionsPageAdapter;
import com.example.app_v1.fragments.MeasurementDetailsFragment;
import com.example.app_v1.old.ApiClientTestViewModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Toolbar settings
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        getSupportActionBar().setSubtitle("Greenhouse: GH01");
        /* set settings icon
        Drawable settingsIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_settings);
        toolbar.setOverflowIcon(settingsIcon);
        */

        //Enable back-arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Init sections page adapter and setup with page viewer
        viewPager = findViewById(R.id.viewPager);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPageAdapter);
        setupViewPager(viewPager);

        //Set up tab layout with view pager and add icons to tabs
        tabLayout = findViewById(R.id.measurementDetailsTabs);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);

        //Dummy tab titles
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("T: 25.5 \u2103");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("H: 35 %");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("650 ppm");

        measurementDetailsActivityViewModel = ViewModelProviders.of(this).get(MeasurementDetailsActivityViewModel.class);
        measurementDetailsActivityViewModel.initViewModel();

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

                String msg = "Settings";
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
