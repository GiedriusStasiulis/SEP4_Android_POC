package com.example.app_v1.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.app_v1.R;
import com.example.app_v1.adapters.TemperatureRVAdapter;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.viewmodels.MeasurementDetailsActivityViewModel;

import java.util.ArrayList;

public class MeasurementDetailsFragment extends Fragment
{
    private static final String TAG = "MeasurementDetailsFragment";

    protected TextView titleMeasurementOverview;
    protected TextView titleMeasurementHistory;
    protected TextView titleLatestMeasurementValue;
    protected TextView valueLatestMeasurement;
    protected TextView titleTimestamp;
    protected TextView valueTimestamp;
    protected TextView titleAlarmThresholds;
    protected TextView titleMinAlarmThreshold;
    protected TextView valueMinAlarmThreshold;
    protected TextView titleMaxAlarmThreshold;
    protected TextView valueMaxAlarmThreshold;
    protected TextView symbolMeasurementValue;
    protected TextView symbolMinAlarmThreshold;
    protected TextView symbolMaxAlarmThreshold;
    protected ConstraintLayout measurementOverviewDisplay;
    protected ConstraintLayout measurementHistoryDisplay;
    protected ImageButton openHistoryDialogBtn;
    protected Button btnOpenDialogDateTimeFrom;
    protected Button btnOpenDialogDateTimeTo;
    protected Button btnShowHistory;
    protected ToggleButton toggleBtnMeasurementDisplay;
    protected ToggleButton toggleBtnMeasurementHistoryBtn;
    public ScrollView scrollView;
    private RecyclerView measurementHistoryRecyclerView;

    protected MeasurementDetailsActivityViewModel measurementDetailsActivityViewModel;

    private TemperatureRVAdapter temperatureRVAdapter;
    private ArrayList<Temperature> temperatureHistory;

    private Parcelable rvState;

    private void initTemperatureRecyclerView()
    {
        temperatureRVAdapter = new TemperatureRVAdapter(getActivity());

        measurementHistoryRecyclerView.hasFixedSize();
        measurementHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        measurementHistoryRecyclerView.setAdapter(temperatureRVAdapter);
    }

    private void initHumidityRecyclerView()
    {

    }

    private void initCO2RecyclerView()
    {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.measurement_details_fragment,container,false);

        titleMeasurementOverview = view.findViewById(R.id.titleMeasurementOverview);
        titleMeasurementHistory = view.findViewById(R.id.titleMeasurementHistory);
        titleLatestMeasurementValue = view.findViewById(R.id.titleLatestMeasurementValue);
        valueLatestMeasurement = view.findViewById(R.id.valueLatestMeasurement);
        titleTimestamp = view.findViewById(R.id.titleTimestamp);
        valueTimestamp = view.findViewById(R.id.valueTimestamp);
        titleAlarmThresholds = view.findViewById(R.id.titleAlarmThresholds);
        titleMinAlarmThreshold = view.findViewById(R.id.titleMinAlarmThreshold);
        valueMinAlarmThreshold = view.findViewById(R.id.valueMinAlarmThreshold);
        titleMaxAlarmThreshold = view.findViewById(R.id.titleMaxAlarmThreshold);
        valueMaxAlarmThreshold = view.findViewById(R.id.valueMaxAlarmThreshold);
        symbolMeasurementValue = view.findViewById(R.id.symbolMeasurementValue);
        symbolMinAlarmThreshold = view.findViewById(R.id.symbolMinAlarmThreshold);
        symbolMaxAlarmThreshold = view.findViewById(R.id.symbolMaxAlarmThreshold);
        measurementOverviewDisplay = view.findViewById(R.id.measurementOverviewDisplay);
        measurementHistoryDisplay = view.findViewById(R.id.measurementHistoryDisplay);
        openHistoryDialogBtn = view.findViewById(R.id.openHistoryDialogBtn);
        btnOpenDialogDateTimeFrom = view.findViewById(R.id.btnOpenDialogDateTimeFrom);
        btnOpenDialogDateTimeTo = view.findViewById(R.id.btnOpenDialogDateTimeTo);
        btnShowHistory = view.findViewById(R.id.btnShowHistory);
        toggleBtnMeasurementDisplay = view.findViewById(R.id.toggleBtnMeasurementDisplay);
        toggleBtnMeasurementDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
        toggleBtnMeasurementHistoryBtn = view.findViewById(R.id.toggleMeasurementHistoryBtn);
        toggleBtnMeasurementHistoryBtn.setBackgroundResource(R.drawable.icon_arrow_up);
        scrollView = view.findViewById(R.id.scrollView);

        measurementHistoryRecyclerView = view.findViewById(R.id.measurementHistoryRecyclerView);

        initTemperatureRecyclerView();

        measurementDetailsActivityViewModel = ViewModelProviders.of(this.getActivity()).get(MeasurementDetailsActivityViewModel.class);

        measurementDetailsActivityViewModel.getLatestTemperatures().observe(this, new Observer<ArrayList<Temperature>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Temperature> temperatures)
            {
                temperatureHistory = new ArrayList<>();
                temperatureHistory.addAll(temperatures);
            }
        });

        measurementDetailsActivityViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(@Nullable Integer integer)
            {
                switch(integer)
                {
                    case 0:

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_temperature_display));
                        titleMeasurementHistory.setText(getResources().getString(R.string.title_temperature_history));
                        valueLatestMeasurement.setText(getResources().getString(R.string.value_temperature));
                        valueMinAlarmThreshold.setText(getResources().getString(R.string.value_min_alarm_temp_threshold));
                        valueMaxAlarmThreshold.setText(getResources().getString(R.string.value_max_alarm_temp_threshold));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_temperature));
                        symbolMinAlarmThreshold.setText(getResources().getString(R.string.symbol_temperature));
                        symbolMaxAlarmThreshold.setText(getResources().getString(R.string.symbol_temperature));

                        initTemperatureRecyclerView();

                        btnShowHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on show temperature history button",
                                        Toast.LENGTH_LONG).show();

                                temperatureRVAdapter.clearItems();
                                temperatureRVAdapter.setItems(temperatureHistory);
                            }
                        });

                        openHistoryDialogBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on open temperature history button",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                        break;

                    case 1:

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_humidity_display));
                        titleMeasurementHistory.setText(getResources().getString(R.string.title_humidity_history));
                        valueLatestMeasurement.setText(getResources().getString(R.string.value_humidity));
                        valueMinAlarmThreshold.setText(getResources().getString(R.string.value_min_alarm_hum_threshold));
                        valueMaxAlarmThreshold.setText(getResources().getString(R.string.value_max_alarm_hum_threshold));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_humidity));
                        symbolMinAlarmThreshold.setText(getResources().getString(R.string.symbol_humidity));
                        symbolMaxAlarmThreshold.setText(getResources().getString(R.string.symbol_humidity));

                        btnShowHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on show humidity history button",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                        openHistoryDialogBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on open humidity history button",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                        break;

                    case 2:

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_co2_display));
                        titleMeasurementHistory.setText(getResources().getString(R.string.title_co2_history));
                        valueLatestMeasurement.setText(getResources().getString(R.string.value_co2));
                        valueMinAlarmThreshold.setText(getResources().getString(R.string.value_min_alarm_co2_threshold));
                        valueMaxAlarmThreshold.setText(getResources().getString(R.string.value_max_alarm_co2_threshold));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_co2));
                        symbolMinAlarmThreshold.setText(getResources().getString(R.string.symbol_co2));
                        symbolMaxAlarmThreshold.setText(getResources().getString(R.string.symbol_co2));

                        btnShowHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on show CO2 history button",
                                        Toast.LENGTH_LONG).show();

                            }
                        });

                        openHistoryDialogBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on open CO2 history button",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                        break;

                    default:
                        break;
                }
            }
        });

        toggleBtnMeasurementDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    measurementOverviewDisplay.setVisibility(View.GONE);
                    toggleBtnMeasurementDisplay.setBackgroundResource(R.drawable.icon_arrow_down);
                }

                else
                {
                    measurementOverviewDisplay.setVisibility(View.VISIBLE);
                    toggleBtnMeasurementDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
                }
            }
        });

        toggleBtnMeasurementHistoryBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    measurementHistoryDisplay.setVisibility(View.GONE);
                    measurementHistoryRecyclerView.setVisibility(View.GONE);
                    toggleBtnMeasurementHistoryBtn.setBackgroundResource(R.drawable.icon_arrow_down);
                }

                else
                {
                    measurementHistoryDisplay.setVisibility(View.VISIBLE);
                    measurementHistoryRecyclerView.setVisibility(View.VISIBLE);
                    toggleBtnMeasurementHistoryBtn.setBackgroundResource(R.drawable.icon_arrow_up);
                }
            }
        });

        btnOpenDialogDateTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Clicked on set datetime from button",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnOpenDialogDateTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Clicked on set datetime to button",
                        Toast.LENGTH_LONG).show();
            }
        });

        //Return stored toggle button states after phone orientation has been changed
        if(savedInstanceState != null)
        {
            toggleBtnMeasurementDisplay.setChecked(savedInstanceState.getBoolean("toggleMeasurementDisplayBtn_state"));
            toggleBtnMeasurementHistoryBtn.setChecked(savedInstanceState.getBoolean("toggleMeasurementHistoryBtn_state"));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        //Save toggle button states when changing phone orientation
        outState.putBoolean("toggleMeasurementDisplayBtn_state",toggleBtnMeasurementDisplay.isChecked());
        outState.putBoolean("toggleMeasurementHistoryBtn_state",toggleBtnMeasurementHistoryBtn.isChecked());

        super.onSaveInstanceState(outState);
    }
}