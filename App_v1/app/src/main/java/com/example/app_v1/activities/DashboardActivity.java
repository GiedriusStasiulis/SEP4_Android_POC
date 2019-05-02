package com.example.app_v1.activities;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.app_v1.R;
import com.example.app_v1.adapters.SectionsPageAdapter;
import com.example.app_v1.fragments.Co2Fragment;
import com.example.app_v1.fragments.HumidityFragment;
import com.example.app_v1.fragments.TemperatureFragment;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity
{
    private static final String TAG = "DashboardActivity";

    public Toolbar toolbar;
    private TabLayout tabLayout;
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

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
        /* set settings icon
        Drawable settingsIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_settings);
        toolbar.setOverflowIcon(settingsIcon);
        */

        //Enable back-arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Init sections page adapter and setup with page viewer
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        //Set up tab layout with view pager and add icons to tabs
        tabLayout = findViewById(R.id.measurementDetailsTabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TemperatureFragment(), "25\u2103");
        adapter.addFragment(new HumidityFragment(), "35%");
        adapter.addFragment(new Co2Fragment(), "650 ppm");

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
}
