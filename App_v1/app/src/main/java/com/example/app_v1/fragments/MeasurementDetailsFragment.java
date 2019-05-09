package com.example.app_v1.fragments;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.app_v1.R;
import com.example.app_v1.activities.MeasurementHistoryActivity;
import com.example.app_v1.adapters.Co2RVAdapter;
import com.example.app_v1.adapters.HumidityRVAdapter;
import com.example.app_v1.adapters.TemperatureRVAdapter;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.viewmodels.DashboardActivityViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.ParseException;
import java.util.ArrayList;

public class MeasurementDetailsFragment extends Fragment
{
    private static final String TAG = "MeasurementDetailsFragment";

    private int selectedGreenhouseId;

    private TextView titleMeasurementOverview;
    private TextView valueLatestMeasurement;
    private TextView valueMeasurementTime;
    private TextView valueMeasurementDate;
    private TextView symbolMeasurementValue;

    private ConstraintLayout measurementOverviewDisplay;
    private ConstraintLayout recentMeasurementDisplay;
    private ConstraintLayout thresholdSettingsDisplay;

    private ConstraintLayout measurementOverviewDisplayContents;
    private ConstraintLayout recentMeasurementDisplayContent;
    private ConstraintLayout thresholdDisplayContent;

    private ConstraintLayout measurementOverviewLoadingScreen;
    private ConstraintLayout latestMeasurementsLoadingScreen;
    private ConstraintLayout thresholdsLoadingScreen;

    private ImageButton btnOpenHistoryActivity;
    private ImageButton btnOpenThresholdsSettings;

    private ToggleButton toggleBtnMeasurementOverviewDisplay;
    private ToggleButton toggleBtnRecentMeasurementDisplay;
    private ToggleButton toggleBtnThresholdsDisplay;
    private RadioButton radioBtnShowRecentList;
    private RadioButton radioBtnShowRecentGraph;

    private GraphView graphView;
    private LineGraphSeries<DataPoint> series;

    private ProgressBar progressBarMeasurementOverview;
    private ProgressBar progressBarLatestValues;
    private ProgressBar progressBarThresholds;

    private RecyclerView recentMeasurementRView;
    private TemperatureRVAdapter temperatureRVAdapter;
    private HumidityRVAdapter humidityRVAdapter;
    private Co2RVAdapter co2RVAdapter;

    private ArrayList<Temperature> latestTemperatures = new ArrayList<>();
    private ArrayList<Humidity> latestHumidity = new ArrayList<>();
    private ArrayList<Co2> latestCo2 = new ArrayList<>();

    private DashboardActivityViewModel dashboardActivityViewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.measurement_details_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        titleMeasurementOverview = view.findViewById(R.id.titleMeasurementOverview);
        valueLatestMeasurement = view.findViewById(R.id.valueLatestMeasurement);
        valueMeasurementTime = view.findViewById(R.id.valueMeasurementTime);
        valueMeasurementDate = view.findViewById(R.id.valueMeasurementDate);
        symbolMeasurementValue = view.findViewById(R.id.symbolMeasurementValue);
        measurementOverviewDisplay = view.findViewById(R.id.measurementOverviewDisplay);
        recentMeasurementDisplay = view.findViewById(R.id.recentMeasurementDisplay);
        measurementOverviewDisplayContents = view.findViewById(R.id.measurementOverviewDisplayContents);
        recentMeasurementDisplayContent = view.findViewById(R.id.recentMeasurementDisplayContent);
        thresholdDisplayContent = view.findViewById(R.id.thresholdDisplayContent);
        measurementOverviewLoadingScreen = view.findViewById(R.id.measurementOverviewLoadingScreen);
        latestMeasurementsLoadingScreen = view.findViewById(R.id.latestMeasurementsLoadingScreen);
        thresholdsLoadingScreen = view.findViewById(R.id.thresholdsLoadingScreen);
        thresholdSettingsDisplay = view.findViewById(R.id.thresholdSettingsDisplay);
        btnOpenHistoryActivity = view.findViewById(R.id.btnOpenHistoryActivity);
        btnOpenThresholdsSettings = view.findViewById(R.id.btnOpenThresholdsSettings);
        toggleBtnMeasurementOverviewDisplay = view.findViewById(R.id.toggleBtnMeasurementOverviewDisplay);
        toggleBtnRecentMeasurementDisplay = view.findViewById(R.id.toggleBtnRecentMeasurementDisplay);
        toggleBtnThresholdsDisplay = view.findViewById(R.id.toggleBtnThresholdsDisplay);
        radioBtnShowRecentList = view.findViewById(R.id.radioBtnShowRecentList);
        radioBtnShowRecentGraph = view.findViewById(R.id.radioBtnShowRecentGraph);
        recentMeasurementRView = view.findViewById(R.id.recentMeasurementRView);
        graphView = view.findViewById(R.id.graphView);
        progressBarMeasurementOverview = view.findViewById(R.id.progressBarMeasurementOverview);
        progressBarLatestValues = view.findViewById(R.id.progressBarLatestValues);
        progressBarThresholds = view.findViewById(R.id.progressBarThresholds);

        toggleBtnMeasurementOverviewDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
        toggleBtnRecentMeasurementDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
        toggleBtnThresholdsDisplay.setBackgroundResource(R.drawable.icon_arrow_up);

        radioBtnShowRecentList.setChecked(true);
        radioBtnShowRecentList.jumpDrawablesToCurrentState(); //bug fix for displaying partially checked radio button on last fragment

        graphView.setVisibility(View.GONE);
        recentMeasurementRView.setVisibility(View.VISIBLE);

        View.OnClickListener radioBtnShowRecentList_listener = new View.OnClickListener(){
            public void onClick(View v)
            {
                radioBtnShowRecentGraph.setChecked(false);
                graphView.setVisibility(View.GONE);
                recentMeasurementRView.setVisibility(View.VISIBLE);
            }
        };

        View.OnClickListener radioBtnShowRecentGraph_listener = new View.OnClickListener(){
            public void onClick(View v)
            {
                radioBtnShowRecentList.setChecked(false);
                graphView.setVisibility(View.VISIBLE);
                recentMeasurementRView.setVisibility(View.GONE);
            }
        };

        radioBtnShowRecentList.setOnClickListener(radioBtnShowRecentList_listener);
        radioBtnShowRecentGraph.setOnClickListener(radioBtnShowRecentGraph_listener);

        btnOpenThresholdsSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Clicked on open thresholds settings button",
                        Toast.LENGTH_LONG).show();
            }
        });

        hideLayoutContentBeforeLoading();
        showLoadingScreens();

        dashboardActivityViewModel = ViewModelProviders.of(this.getActivity()).get(DashboardActivityViewModel.class);

        dashboardActivityViewModel.getSelectedGreenhouseId().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                selectedGreenhouseId = integer;
            }
        });

        dashboardActivityViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(@Nullable Integer integer)
            {
                switch(integer)
                {
                    case 0:

                        initRecentTemperatureRView();
                        graphView.removeAllSeries();

                        dashboardActivityViewModel.getLatestMeasurementsFromRepo().observe(getActivity(), new Observer<ArrayList<Measurement>>()
                        {
                            @Override
                            public void onChanged(final ArrayList<Measurement> measurements)
                            {
                                dashboardActivityViewModel.getIsLoading().observe(getActivity(), new Observer<Boolean>()
                                {
                                    @Override
                                    public void onChanged(Boolean aBoolean)
                                    {
                                        if(!aBoolean)
                                        {
                                            hideLoadingScreens();
                                            showLayoutContentAfterLoading();
                                        }
                                        else
                                        {
                                            hideLayoutContentBeforeLoading();
                                            showLoadingScreens();
                                        }
                                    }
                                });

                                try {
                                    latestTemperatures = dashboardActivityViewModel.extractLatestTemperaturesFromMeasurements(measurements);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if(!measurements.isEmpty())
                                {
                                    valueLatestMeasurement.setText(latestTemperatures.get(0).getTemperature());
                                    valueMeasurementTime.setText(latestTemperatures.get(0).getTime());
                                    valueMeasurementDate.setText(latestTemperatures.get(0).getDate());

                                    initTemperatureGraphView(latestTemperatures);

                                    temperatureRVAdapter.clearItems();
                                    temperatureRVAdapter.setItems(latestTemperatures);
                                }

                                else
                                {
                                    valueLatestMeasurement.setText("N/A");
                                    valueMeasurementTime.setText("N/A");
                                    valueMeasurementDate.setText("N/A");
                                    temperatureRVAdapter.clearItems();
                                }
                            }
                        });

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_temperature_display));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_temperature));

                        btnOpenHistoryActivity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String measurementType = "Temperature";
                                Intent intent = new Intent(getContext(), MeasurementHistoryActivity.class);
                                intent.putExtra("measurement_type",measurementType);
                                intent.putExtra("selectedGreenhouseId",selectedGreenhouseId);
                                startActivity(intent);
                            }
                        });

                        break;

                    case 1:

                        initRecentHumidityRView();
                        graphView.removeAllSeries();

                        dashboardActivityViewModel.getLatestMeasurementsFromRepo().observe(getActivity(), new Observer<ArrayList<Measurement>>()
                        {
                            @Override
                            public void onChanged(ArrayList<Measurement> measurements)
                            {
                                try {
                                    latestHumidity = dashboardActivityViewModel.extractLatestHumidityFromMeasurements(measurements);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                valueLatestMeasurement.setText(latestHumidity.get(0).getHumidity());
                                valueMeasurementTime.setText(latestHumidity.get(0).getTime());
                                valueMeasurementDate.setText(latestHumidity.get(0).getDate());

                                initHumidityGraphView(latestHumidity);

                                humidityRVAdapter.clearItems();
                                humidityRVAdapter.setItems(latestHumidity);
                            }
                        });

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_humidity_display));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_humidity));

                        btnOpenHistoryActivity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String measurementType = "Humidity";
                                Intent intent = new Intent(getContext(), MeasurementHistoryActivity.class);
                                intent.putExtra("measurement_type",measurementType);
                                intent.putExtra("selectedGreenhouseId",selectedGreenhouseId);
                                startActivity(intent);
                            }
                        });

                        break;

                    case 2:

                        initRecentCO2RecyclerView();
                        graphView.removeAllSeries();

                        dashboardActivityViewModel.getLatestMeasurementsFromRepo().observe(getActivity(), new Observer<ArrayList<Measurement>>()
                        {
                            @Override
                            public void onChanged(ArrayList<Measurement> measurements)
                            {
                                try {
                                    latestCo2 = dashboardActivityViewModel.extractLatestCo2FromMeasurements(measurements);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                valueLatestMeasurement.setText(latestCo2.get(0).getCo2());
                                valueMeasurementTime.setText(latestCo2.get(0).getTime());
                                valueMeasurementDate.setText(latestCo2.get(0).getDate());

                                initCo2GraphView(latestCo2);

                                co2RVAdapter.clearItems();
                                co2RVAdapter.setItems(latestCo2);
                            }
                        });

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_co2_display));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_co2));

                        btnOpenHistoryActivity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String measurementType = "Co2";
                                Intent intent = new Intent(getContext(), MeasurementHistoryActivity.class);
                                intent.putExtra("measurement_type",measurementType);
                                intent.putExtra("selectedGreenhouseId",selectedGreenhouseId);
                                startActivity(intent);
                            }
                        });

                        break;

                    default:
                        break;
                }
            }
        });

        toggleBtnMeasurementOverviewDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    measurementOverviewDisplay.setVisibility(View.GONE);
                    toggleBtnMeasurementOverviewDisplay.setBackgroundResource(R.drawable.icon_arrow_down);
                }

                else
                {
                    measurementOverviewDisplay.setVisibility(View.VISIBLE);
                    toggleBtnMeasurementOverviewDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
                }
            }
        });

        toggleBtnRecentMeasurementDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    recentMeasurementDisplay.setVisibility(View.GONE);
                    toggleBtnRecentMeasurementDisplay.setBackgroundResource(R.drawable.icon_arrow_down);
                }

                else
                {
                    recentMeasurementDisplay.setVisibility(View.VISIBLE);
                    toggleBtnRecentMeasurementDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
                }
            }
        });

        toggleBtnThresholdsDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    thresholdSettingsDisplay.setVisibility(View.GONE);
                    toggleBtnThresholdsDisplay.setBackgroundResource(R.drawable.icon_arrow_down);
                }

                else
                {
                    thresholdSettingsDisplay.setVisibility(View.VISIBLE);
                    toggleBtnThresholdsDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
                }
            }
        });

        //Return stored toggle button states after phone orientation has been changed
        if(savedInstanceState != null)
        {
            toggleBtnMeasurementOverviewDisplay.setChecked(savedInstanceState.getBoolean("toggleMeasurementDisplayBtn_state"));
            toggleBtnRecentMeasurementDisplay.setChecked(savedInstanceState.getBoolean("toggleMeasurementHistoryBtn_state"));
            toggleBtnThresholdsDisplay.setChecked(savedInstanceState.getBoolean("toggleThresholdSettingsBtn_state"));

            radioBtnShowRecentList.setChecked(savedInstanceState.getBoolean("radioBtnShowList_state"));
            radioBtnShowRecentGraph.setChecked(savedInstanceState.getBoolean("radioBtnShowGraph_state"));
        }
    }

    private void hideLayoutContentBeforeLoading()
    {
        measurementOverviewDisplayContents.setVisibility(View.GONE);
        recentMeasurementDisplayContent.setVisibility(View.GONE);
        thresholdDisplayContent.setVisibility(View.GONE);
    }

    private void showLayoutContentAfterLoading()
    {
        measurementOverviewDisplayContents.setVisibility(View.VISIBLE);
        recentMeasurementDisplayContent.setVisibility(View.VISIBLE);
        thresholdDisplayContent.setVisibility(View.VISIBLE);
    }

    private void showLoadingScreens()
    {
        measurementOverviewLoadingScreen.setVisibility(View.VISIBLE);
        latestMeasurementsLoadingScreen.setVisibility(View.VISIBLE);
        thresholdsLoadingScreen.setVisibility(View.VISIBLE);
        progressBarMeasurementOverview.setVisibility(View.VISIBLE);
        progressBarLatestValues.setVisibility(View.VISIBLE);
        progressBarThresholds.setVisibility(View.VISIBLE);
    }

    private void hideLoadingScreens()
    {
        measurementOverviewLoadingScreen.setVisibility(View.GONE);
        latestMeasurementsLoadingScreen.setVisibility(View.GONE);
        thresholdsLoadingScreen.setVisibility(View.GONE);
        progressBarMeasurementOverview.setVisibility(View.GONE);
        progressBarLatestValues.setVisibility(View.GONE);
        progressBarThresholds.setVisibility(View.GONE);
    }

    private void initRecentTemperatureRView()
    {
        temperatureRVAdapter = new TemperatureRVAdapter(getActivity());

        temperatureRVAdapter.clearItems();
        recentMeasurementRView.hasFixedSize();
        recentMeasurementRView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recentMeasurementRView.setAdapter(temperatureRVAdapter);
    }

    private void initRecentHumidityRView()
    {
        humidityRVAdapter = new HumidityRVAdapter(getActivity());

        humidityRVAdapter.clearItems();
        recentMeasurementRView.hasFixedSize();
        recentMeasurementRView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recentMeasurementRView.setAdapter(humidityRVAdapter);
    }

    private void initRecentCO2RecyclerView()
    {
        co2RVAdapter = new Co2RVAdapter(getActivity());

        co2RVAdapter.clearItems();
        recentMeasurementRView.hasFixedSize();
        recentMeasurementRView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recentMeasurementRView.setAdapter(co2RVAdapter);
    }

    private void initTemperatureGraphView(ArrayList<Temperature> temperatures)
    {
        if(temperatures != null)
        {
            graphView.removeAllSeries();

            series = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, Double.valueOf(temperatures.get(4).getTemperature())),
                    new DataPoint(1, Double.valueOf(temperatures.get(3).getTemperature())),
                    new DataPoint(2, Double.valueOf(temperatures.get(2).getTemperature())),
                    new DataPoint(3, Double.valueOf(temperatures.get(1).getTemperature())),
                    new DataPoint(4, Double.valueOf(temperatures.get(0).getTemperature()))
            });

            graphView.addSeries(series);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);

            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
            staticLabelsFormatter.setHorizontalLabels(
                    new String[]{temperatures.get(4).getTime(),
                            temperatures.get(3).getTime(),
                            temperatures.get(2).getTime(),
                            temperatures.get(1).getTime(),
                            temperatures.get(0).getTime()});

            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getGridLabelRenderer().setHumanRounding(true);
            graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
            graphView.getGridLabelRenderer().setVerticalAxisTitleTextSize(30f);
            graphView.getGridLabelRenderer().setLabelVerticalWidth(50);
            graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            graphView.getGridLabelRenderer().reloadStyles();
        }
    }

    private void initHumidityGraphView(ArrayList<Humidity> humiditys)
    {
        if(humiditys != null)
        {
            graphView.removeAllSeries();

            series = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, Double.valueOf(humiditys.get(4).getHumidity())),
                    new DataPoint(1, Double.valueOf(humiditys.get(3).getHumidity())),
                    new DataPoint(2, Double.valueOf(humiditys.get(2).getHumidity())),
                    new DataPoint(3, Double.valueOf(humiditys.get(1).getHumidity())),
                    new DataPoint(4, Double.valueOf(humiditys.get(0).getHumidity()))
            });

            graphView.addSeries(series);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);

            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
            staticLabelsFormatter.setHorizontalLabels(
                    new String[]{humiditys.get(4).getTime(),
                            humiditys.get(3).getTime(),
                            humiditys.get(2).getTime(),
                            humiditys.get(1).getTime(),
                            humiditys.get(0).getTime()});

            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getGridLabelRenderer().setHumanRounding(true);
            graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
            graphView.getGridLabelRenderer().setVerticalAxisTitleTextSize(30f);
            graphView.getGridLabelRenderer().setLabelVerticalWidth(50);
            graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            graphView.getGridLabelRenderer().reloadStyles();
        }
    }

    private void initCo2GraphView(ArrayList<Co2> co2s)
    {
        if(co2s != null)
        {
            graphView.removeAllSeries();

            series = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, Double.valueOf(co2s.get(4).getCo2())),
                    new DataPoint(1, Double.valueOf(co2s.get(3).getCo2())),
                    new DataPoint(2, Double.valueOf(co2s.get(2).getCo2())),
                    new DataPoint(3, Double.valueOf(co2s.get(1).getCo2())),
                    new DataPoint(4, Double.valueOf(co2s.get(0).getCo2()))
            });

            graphView.addSeries(series);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);

            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
            staticLabelsFormatter.setHorizontalLabels(
                    new String[]{co2s.get(4).getTime(),
                            co2s.get(3).getTime(),
                            co2s.get(2).getTime(),
                            co2s.get(1).getTime(),
                            co2s.get(0).getTime()});

            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getGridLabelRenderer().setHumanRounding(true);
            graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
            graphView.getGridLabelRenderer().setVerticalAxisTitleTextSize(30f);
            graphView.getGridLabelRenderer().setLabelVerticalWidth(50);
            graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            graphView.getGridLabelRenderer().reloadStyles();
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        //Save toggle button states when changing phone orientation
        outState.putBoolean("toggleMeasurementDisplayBtn_state", toggleBtnMeasurementOverviewDisplay.isChecked());
        outState.putBoolean("toggleMeasurementHistoryBtn_state", toggleBtnRecentMeasurementDisplay.isChecked());
        outState.putBoolean("toggleThresholdSettingsBtn_state", toggleBtnThresholdsDisplay.isChecked());

        outState.putBoolean("radioBtnShowList_state", radioBtnShowRecentList.isChecked());
        outState.putBoolean("radioBtnShowGraph_state", radioBtnShowRecentGraph.isChecked());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}