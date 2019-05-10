package com.example.app_v1.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.preference.PreferenceFragmentCompat;

import com.example.app_v1.R;

public class SettingsActivity extends AppCompatActivity {

    private int selectedGreenhouseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            selectedGreenhouseId = bundle.getInt("selectedGreenhouseId");
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //Enable back-arrow
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home )
        {
            Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
            intent.putExtra("selectedGreenhouseId",selectedGreenhouseId);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            // notification preference change listener



        }
    }
}
