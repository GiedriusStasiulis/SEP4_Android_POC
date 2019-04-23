package com.example.app_v1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.app_v1.models.Temperature;
import com.example.app_v1.viewmodels.ApiClientTestViewModel;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    TextView tempTextView;
    TextView tStampTextView;
    TextView updatedTextView;

    private ApiClientTestViewModel apiClientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tempTextView = findViewById(R.id.temperature);
        tStampTextView = findViewById(R.id.timestamp);
        updatedTextView = findViewById(R.id.updated);

        apiClientViewModel = ViewModelProviders.of(this).get(ApiClientTestViewModel.class);

        apiClientViewModel.init();

        apiClientViewModel.getTemp().observe(this, new Observer<Temperature>() {

            @Override
            public void onChanged(@Nullable Temperature temperature)
            {
                assert temperature != null;

                tempTextView.setText(String.format("Temperature: %s \u2103", temperature.getTemperature()));
                tStampTextView.setText(String.format("Timestamp: %s", temperature.getDateTime()));
                updatedTextView.setText(String.format("Updated: %s", getCurrentTimeDate()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getCurrentTimeDate()
    {
        DateFormat df = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
        Date currentDateTime = Calendar.getInstance().getTime();

        String dateTimeNowStr = df.format(currentDateTime);

        return dateTimeNowStr;
    }
}
