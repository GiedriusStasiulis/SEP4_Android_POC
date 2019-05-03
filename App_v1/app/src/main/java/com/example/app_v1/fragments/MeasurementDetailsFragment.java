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
    protected TextView symbol_measurement;
    protected TextView value_timestamp;
    protected TextView title_last_updated;
    protected TextView value_last_updated;
    protected ConstraintLayout measurementDisplay;
    protected ImageButton openGraphBtn;

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
                        symbol_measurement.setText(getResources().getString(R.string.symbol_temperature_celsius));

                        break;

                    case 1:
                        title_measurement.setText(getResources().getString(R.string.title_humidity));
                        value_measurement.setText(getResources().getString(R.string.value_humidity));
                        symbol_measurement.setText(getResources().getString(R.string.symbol_humidity_percentage));

                        break;

                    case 2:
                        title_measurement.setText(getResources().getString(R.string.title_co2));
                        value_measurement.setText(getResources().getString(R.string.value_co2));
                        symbol_measurement.setText(getResources().getString(R.string.symbol_co2_ppm));

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
        symbol_measurement = view.findViewById(R.id.symbol_measurement);
        title_timestamp = view.findViewById(R.id.title_timestamp);
        value_timestamp = view.findViewById(R.id.value_timestamp);
        title_last_updated = view.findViewById(R.id.title_last_updated);
        value_last_updated = view.findViewById(R.id.value_last_updated);

        measurementDisplay = view.findViewById(R.id.measurementDisplay);

        openGraphBtn = view.findViewById(R.id.open_graph_btn);


        openGraphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Clicked on graph button",
                        Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}