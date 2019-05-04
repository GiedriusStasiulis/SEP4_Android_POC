package com.example.app_v1.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_v1.R;
import com.example.app_v1.viewmodels.MeasurementDetailsActivityViewModel;

public class MeasurementDetailsFragment extends Fragment
{
    private static final String TAG = "MeasurementDetailsFragment";

    protected TextView title_measurement;
    protected TextView value_measurement;
    protected TextView title_timestamp;
    protected TextView value_timestamp;
    protected TextView title_last_updated;
    protected TextView value_last_updated;
    protected TextView title_alarm_thresholds;
    protected TextView title_min_alarm_threshold;
    protected TextView value_min_alarm_threshold;
    protected TextView title_max_alarm_threshold;
    protected TextView value_max_alarm_threshold;
    protected TextView symbol_measurement_value;
    protected TextView symbol_min_alarm_threshold;
    protected TextView symbol_max_alarm_threshold;
    protected ConstraintLayout measurementDisplay;
    protected ImageButton openGraphBtn;
    protected ImageButton openSettingsBtn;

    protected MeasurementDetailsActivityViewModel measurementDetailsActivityViewModel;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        measurementDetailsActivityViewModel = ViewModelProviders.of(this.getActivity()).get(MeasurementDetailsActivityViewModel.class);
        measurementDetailsActivityViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(@Nullable Integer integer)
            {
                switch(integer)
                {
                    case 0:
                        title_measurement.setText(getResources().getString(R.string.title_temperature));
                        value_measurement.setText(getResources().getString(R.string.value_temperature));
                        value_min_alarm_threshold.setText(getResources().getString(R.string.value_min_alarm_temp_threshold));
                        value_max_alarm_threshold.setText(getResources().getString(R.string.value_max_alarm_temp_threshold));
                        symbol_measurement_value.setText(getResources().getString(R.string.symbol_temperature));
                        symbol_min_alarm_threshold.setText(getResources().getString(R.string.symbol_temperature));
                        symbol_max_alarm_threshold.setText(getResources().getString(R.string.symbol_temperature));

                        break;

                    case 1:
                        title_measurement.setText(getResources().getString(R.string.title_humidity));
                        value_measurement.setText(getResources().getString(R.string.value_humidity));
                        value_min_alarm_threshold.setText(getResources().getString(R.string.value_min_alarm_hum_threshold));
                        value_max_alarm_threshold.setText(getResources().getString(R.string.value_max_alarm_hum_threshold));
                        symbol_measurement_value.setText(getResources().getString(R.string.symbol_humidity));
                        symbol_min_alarm_threshold.setText(getResources().getString(R.string.symbol_humidity));
                        symbol_max_alarm_threshold.setText(getResources().getString(R.string.symbol_humidity));

                        break;

                    case 2:
                        title_measurement.setText(getResources().getString(R.string.title_co2));
                        value_measurement.setText(getResources().getString(R.string.value_co2));
                        value_min_alarm_threshold.setText(getResources().getString(R.string.value_min_alarm_co2_threshold));
                        value_max_alarm_threshold.setText(getResources().getString(R.string.value_max_alarm_co2_threshold));
                        symbol_measurement_value.setText(getResources().getString(R.string.symbol_co2));
                        symbol_min_alarm_threshold.setText(getResources().getString(R.string.symbol_co2));
                        symbol_max_alarm_threshold.setText(getResources().getString(R.string.symbol_co2));

                        break;

                        default:
                            break;
                }
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.measurement_details_fragment,container,false);

        title_measurement = view.findViewById(R.id.title_measurement);
        value_measurement = view.findViewById(R.id.value_measurement);
        title_timestamp = view.findViewById(R.id.title_timestamp);
        value_timestamp = view.findViewById(R.id.value_timestamp);
        title_last_updated = view.findViewById(R.id.title_last_updated);
        value_last_updated = view.findViewById(R.id.value_last_updated);
        title_alarm_thresholds = view.findViewById(R.id.title_alarm_thresholds);
        title_min_alarm_threshold = view.findViewById(R.id.title_min_alarm_threshold);
        value_min_alarm_threshold = view.findViewById(R.id.value_min_alarm_threshold);
        title_max_alarm_threshold = view.findViewById(R.id.title_max_alarm_threshold);
        value_max_alarm_threshold = view.findViewById(R.id.value_max_alarm_threshold);
        symbol_measurement_value = view.findViewById(R.id.symbol_measurement_value);
        symbol_min_alarm_threshold = view.findViewById(R.id.symbol_min_alarm_threshold);
        symbol_max_alarm_threshold = view.findViewById(R.id.symbol_max_alarm_threshold);

        measurementDisplay = view.findViewById(R.id.measurementDisplay);

        openGraphBtn = view.findViewById(R.id.open_graph_btn);
        openSettingsBtn = view.findViewById(R.id.open_settings_btn);


        openGraphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Clicked on graph button",
                        Toast.LENGTH_LONG).show();
            }
        });

        openSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Clicked on settings button",
                        Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}