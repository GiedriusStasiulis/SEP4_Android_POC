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
import com.example.app_v1.models.Temperature;
import com.example.app_v1.viewmodels.MeasurementDetailsActivityViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.ParseException;
import java.util.ArrayList;

public class MeasurementDetailsFragment extends Fragment
{
    private static final String TAG = "MeasurementDetailsFragment";

    protected TextView titleMeasurementOverview;
    protected TextView titleMeasurementHistory;
    protected TextView valueLatestMeasurement;
    protected TextView valueMeasurementTime;
    protected TextView valueMeasurementDate;
    protected TextView valueMinAlarmThreshold;
    protected TextView valueMaxAlarmThreshold;
    protected TextView symbolMeasurementValue;
    protected TextView symbolMinAlarmThreshold;
    protected TextView symbolMaxAlarmThreshold;

    protected ConstraintLayout measurementOverviewDisplay;
    protected ConstraintLayout recentMeasurementDisplay;
    protected ConstraintLayout thresholdSettingsDisplay;

    protected ImageButton btnOpenHistoryActivity;
    protected ImageButton btnOpenThresholdsSettings;

    protected ToggleButton toggleBtnMeasurementOverviewDisplay;
    protected ToggleButton toggleBtnRecentMeasurementDisplay;
    protected ToggleButton toggleBtnThresholdsDisplay;
    protected RadioButton radioBtnShowRecentList;
    protected RadioButton radioBtnShowRecentGraph;

    public ScrollView scrollView;

    private GraphView graphView;
    private LineGraphSeries<DataPoint> series;

    protected MeasurementDetailsActivityViewModel measurementDetailsActivityViewModel;

    private RecyclerView recentMeasurementRView;
    private TemperatureRVAdapter temperatureRVAdapter;
    private HumidityRVAdapter humidityRVAdapter;
    private Co2RVAdapter co2RVAdapter;

    private ArrayList<Temperature> recentTemperatures;
    private ArrayList<Humidity> recentHumiditys;
    private ArrayList<Co2> recentCo2s;

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
        titleMeasurementHistory = view.findViewById(R.id.titleMeasurementHistory);
        valueLatestMeasurement = view.findViewById(R.id.valueLatestMeasurement);
        valueMeasurementTime = view.findViewById(R.id.valueMeasurementTime);
        valueMeasurementDate = view.findViewById(R.id.valueMeasurementDate);
        symbolMeasurementValue = view.findViewById(R.id.symbolMeasurementValue);
        measurementOverviewDisplay = view.findViewById(R.id.measurementOverviewDisplay);
        recentMeasurementDisplay = view.findViewById(R.id.recentMeasurementDisplay);
        thresholdSettingsDisplay = view.findViewById(R.id.thresholdSettingsDisplay);
        btnOpenHistoryActivity = view.findViewById(R.id.btnOpenHistoryActivity);
        btnOpenThresholdsSettings = view.findViewById(R.id.btnOpenThresholdsSettings);
        toggleBtnMeasurementOverviewDisplay = view.findViewById(R.id.toggleBtnMeasurementOverviewDisplay);
        toggleBtnRecentMeasurementDisplay = view.findViewById(R.id.toggleBtnRecentMeasurementDisplay);
        toggleBtnThresholdsDisplay = view.findViewById(R.id.toggleBtnThresholdsDisplay);
        radioBtnShowRecentList = view.findViewById(R.id.radioBtnShowRecentList);
        radioBtnShowRecentGraph = view.findViewById(R.id.radioBtnShowRecentGraph);
        scrollView = view.findViewById(R.id.scrollView);
        recentMeasurementRView = view.findViewById(R.id.recentMeasurementRView);
        graphView = view.findViewById(R.id.graphView);

        toggleBtnMeasurementOverviewDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
        toggleBtnRecentMeasurementDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
        toggleBtnThresholdsDisplay.setBackgroundResource(R.drawable.icon_arrow_up);

        radioBtnShowRecentList.setChecked(true);

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

        measurementDetailsActivityViewModel = ViewModelProviders.of(this.getActivity()).get(MeasurementDetailsActivityViewModel.class);

        measurementDetailsActivityViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(@Nullable Integer integer)
            {
                switch(integer)
                {
                    case 0:

                        initRecentTemperatureRView();
                        graphView.removeAllSeries();

                        try {
                            measurementDetailsActivityViewModel.getLatestTemperatures().observe(getActivity(), new Observer<ArrayList<Temperature>>()
                            {
                                @Override
                                public void onChanged(@Nullable ArrayList<Temperature> temperatures)
                                {
                                    recentTemperatures = new ArrayList<>();
                                    recentTemperatures.addAll(temperatures);

                                    valueLatestMeasurement.setText(recentTemperatures.get(0).getTemperature());
                                    valueMeasurementTime.setText(recentTemperatures.get(0).getTime());
                                    valueMeasurementDate.setText(recentTemperatures.get(0).getDate());

                                    initTemperatureGraphView(recentTemperatures);

                                    temperatureRVAdapter.clearItems();
                                    temperatureRVAdapter.setItems(recentTemperatures);
                                }
                            });
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_temperature_display));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_temperature));

                        btnOpenHistoryActivity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                /*Toast.makeText(getActivity(), "Clicked on open temperature history button",
                                        Toast.LENGTH_LONG).show();*/

                                String measurementType = "Temperature";
                                Intent intent = new Intent(getContext(), MeasurementHistoryActivity.class);
                                intent.putExtra("measurement_type",measurementType);
                                startActivity(intent);
                            }
                        });

                        break;

                    case 1:

                        initRecentHumidityRView();
                        graphView.removeAllSeries();

                        try {
                            measurementDetailsActivityViewModel.getLatestHumiditys().observe(getActivity(), new Observer<ArrayList<Humidity>>() {
                                @Override
                                public void onChanged(@Nullable ArrayList<Humidity> humidities)
                                {
                                    recentHumiditys = new ArrayList<>();
                                    recentHumiditys.addAll(humidities);

                                    valueLatestMeasurement.setText(recentHumiditys.get(0).getHumidity());
                                    valueMeasurementTime.setText(recentHumiditys.get(0).getTime());
                                    valueMeasurementDate.setText(recentHumiditys.get(0).getDate());

                                    initHumidityGraphView(recentHumiditys);

                                    humidityRVAdapter.clearItems();
                                    humidityRVAdapter.setItems(recentHumiditys);
                                }
                            });
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_humidity_display));
                        valueLatestMeasurement.setText(getResources().getString(R.string.value_humidity));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_humidity));

                        btnOpenHistoryActivity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on open humidity history button",
                                        Toast.LENGTH_LONG).show();

                                String measurementType = "Humidity";
                                Intent intent = new Intent(getContext(), MeasurementHistoryActivity.class);
                                intent.putExtra("measurement_type",measurementType);
                                startActivity(intent);
                            }
                        });

                        break;

                    case 2:

                        initRecentCO2RecyclerView();
                        graphView.removeAllSeries();

                        try {
                            measurementDetailsActivityViewModel.getLatestCo2s().observe(getActivity(), new Observer<ArrayList<Co2>>()
                            {
                                @Override
                                public void onChanged(@Nullable ArrayList<Co2> co2s)
                                {
                                    recentCo2s = new ArrayList<>();
                                    recentCo2s.addAll(co2s);

                                    valueLatestMeasurement.setText(recentCo2s.get(0).getCo2());
                                    valueMeasurementTime.setText(recentCo2s.get(0).getTime());
                                    valueMeasurementDate.setText(recentCo2s.get(0).getDate());

                                    initCo2GraphView(recentCo2s);

                                    co2RVAdapter.clearItems();
                                    co2RVAdapter.setItems(recentCo2s);
                                }
                            });
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        titleMeasurementOverview.setText(getResources().getString(R.string.title_co2_display));
                        valueLatestMeasurement.setText(getResources().getString(R.string.value_co2));
                        symbolMeasurementValue.setText(getResources().getString(R.string.symbol_co2));

                        btnOpenHistoryActivity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Clicked on open CO2 history button",
                                        Toast.LENGTH_LONG).show();

                                String measurementType = "Co2";
                                Intent intent = new Intent(getContext(), MeasurementHistoryActivity.class);
                                intent.putExtra("measurement_type",measurementType);
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

    @Override
    public void onResume()
    {
        super.onResume();
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
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}