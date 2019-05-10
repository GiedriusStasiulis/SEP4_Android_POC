package com.example.app_v1.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_v1.R;
import com.example.app_v1.adapters.Co2RVAdapter;
import com.example.app_v1.adapters.HumidityRVAdapter;
import com.example.app_v1.adapters.TemperatureRVAdapter;
import com.example.app_v1.dialogs.DateRangePickerFragmentDialog;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.viewmodels.MeasurementHistoryViewModel;

import java.text.ParseException;
import java.util.ArrayList;

public class MeasurementHistoryFragment extends Fragment implements DateRangePickerFragmentDialog.DateRangePickerFragmentDialogListener
{
    private static final String TAG = "MeasurementHistoryFragment";

    private String dateFrom;
    private String timeFrom;
    private String dateTimeFrom;

    private String dateTo;
    private String timeTo;
    private String dateTimeTo;

    private int selectedGreenhouseId;

    private TextView titleMeasurementType;

    private ConstraintLayout measurementHistoryDisplay;

    private Button btnOpenCalendarDialog;
    private Button btnSelectTimeFrom;
    private Button btnSelectTimeTo;

    private ToggleButton toggleBtnHistoryDisplay;

    private ImageButton btnSearchHistory;
    private ImageButton btnClearSearchParams;

    private RecyclerView rvMeasurementHistory;
    private TemperatureRVAdapter temperatureRVAdapter;
    private HumidityRVAdapter humidityRVAdapter;
    private Co2RVAdapter co2RVAdapter;

    private ArrayList<Temperature> temperaturesInDateRange = new ArrayList<>();
    private ArrayList<Humidity> humidityInDateRange = new ArrayList<>();
    private ArrayList<Co2> co2InDateRange = new ArrayList<>();

    private TimePickerDialog.OnTimeSetListener timeFromSetListener;
    private TimePickerDialog.OnTimeSetListener timeToSetListener;

    private MeasurementHistoryViewModel measurementHistoryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.measurement_history_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        titleMeasurementType = view.findViewById(R.id.titleMeasurementType);
        btnOpenCalendarDialog = view.findViewById(R.id.btnOpenCalendarDialog);
        btnSelectTimeFrom = view.findViewById(R.id.btnSelectTimeFrom);
        btnSelectTimeTo = view.findViewById(R.id.btnSelectTimeTo);
        btnSearchHistory = view.findViewById(R.id.btnSearchHistory);
        btnClearSearchParams = view.findViewById(R.id.btnClearSearchParams);
        rvMeasurementHistory = view.findViewById(R.id.rvMeasurementHistory);
        toggleBtnHistoryDisplay = view.findViewById(R.id.toggleBtnHistoryDisplay);
        measurementHistoryDisplay = view.findViewById(R.id.measurementHistoryDisplay);
        toggleBtnHistoryDisplay.setBackgroundResource(R.drawable.icon_arrow_up);

        toggleBtnHistoryDisplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    measurementHistoryDisplay.setVisibility(View.GONE);
                    toggleBtnHistoryDisplay.setBackgroundResource(R.drawable.icon_arrow_down);
                }

                else
                {
                    measurementHistoryDisplay.setVisibility(View.VISIBLE);
                    toggleBtnHistoryDisplay.setBackgroundResource(R.drawable.icon_arrow_up);
                }
            }
        });

        btnOpenCalendarDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePickerDialog();
            }
        });


        measurementHistoryViewModel = ViewModelProviders.of(getActivity()).get(MeasurementHistoryViewModel.class);

        measurementHistoryViewModel.getSelectedGreenhouseId().observe(getActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                selectedGreenhouseId = integer;
            }
        });

        measurementHistoryViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                switch (integer)
                {
                    case 0:

                        initTemperatureHistoryRView();

                        titleMeasurementType.setText(getResources().getString(R.string.title_temperature));

                        btnSearchHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                Toast.makeText(getActivity(), "Searching...",
                                        Toast.LENGTH_LONG).show();

                                /*temperaturesInDateRange.clear();

                                try {
                                    temperaturesInDateRange = measurementHistoryViewModel.getTemperaturesInDateRange(dateTimeFrom,dateTimeTo).getValue();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                temperatureRVAdapter.clearItems();
                                temperatureRVAdapter.setItems(temperaturesInDateRange);*/
                            }
                        });

                        break;

                    case 1:

                        initHumidityHistoryRView();

                        try {
                            humidityInDateRange = measurementHistoryViewModel.getHumidityInDateRange(dateTimeFrom,dateTimeTo);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        humidityRVAdapter.clearItems();
                        humidityRVAdapter.setItems(humidityInDateRange);

                        titleMeasurementType.setText(getResources().getString(R.string.title_humidity));

                        btnSearchHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                humidityInDateRange.clear();

                                try {
                                    humidityInDateRange = measurementHistoryViewModel.getHumidityInDateRange(dateTimeFrom,dateTimeTo);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                humidityRVAdapter.clearItems();
                                humidityRVAdapter.setItems(humidityInDateRange);
                            }
                        });

                        break;

                    case 2:

                        initCo2HistoryRView();

                        try {
                            co2InDateRange = measurementHistoryViewModel.getCo2InDateRange(dateTimeFrom,dateTimeTo);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        co2RVAdapter.clearItems();
                        co2RVAdapter.setItems(co2InDateRange);

                        titleMeasurementType.setText(getResources().getString(R.string.title_co2));

                        btnSearchHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                co2InDateRange.clear();

                                try {
                                    co2InDateRange = measurementHistoryViewModel.getCo2InDateRange(dateTimeFrom,dateTimeTo);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                co2RVAdapter.clearItems();
                                co2RVAdapter.setItems(co2InDateRange);
                            }
                        });

                        break;

                        default:
                            break;
                }
            }
        });

        if(savedInstanceState != null)
        {
            dateTimeFrom = savedInstanceState.getString("dateTimeFrom_value");
            dateTimeTo = savedInstanceState.getString("dateTimeTo_value");

            toggleBtnHistoryDisplay.setChecked(savedInstanceState.getBoolean("toggleHistoryDisplayBtn_state"));
        }
    }

    private void openDatePickerDialog()
    {
        DateRangePickerFragmentDialog dateRangePickerFragmentDialog = new DateRangePickerFragmentDialog();
        dateRangePickerFragmentDialog.setTargetFragment(MeasurementHistoryFragment.this,1);
        dateRangePickerFragmentDialog.show(getFragmentManager(),null);
    }

    private void initTemperatureHistoryRView()
    {
        temperatureRVAdapter = new TemperatureRVAdapter(getActivity());

        temperatureRVAdapter.clearItems();
        rvMeasurementHistory.hasFixedSize();
        rvMeasurementHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMeasurementHistory.setAdapter(temperatureRVAdapter);
    }

    private void initHumidityHistoryRView()
    {
        humidityRVAdapter = new HumidityRVAdapter(getActivity());

        humidityRVAdapter.clearItems();
        rvMeasurementHistory.hasFixedSize();
        rvMeasurementHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMeasurementHistory.setAdapter(humidityRVAdapter);
    }

    private void initCo2HistoryRView()
    {
        co2RVAdapter = new Co2RVAdapter(getActivity());

        co2RVAdapter.clearItems();
        rvMeasurementHistory.hasFixedSize();
        rvMeasurementHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMeasurementHistory.setAdapter(co2RVAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putString("dateTimeFrom_value",dateTimeFrom);
        outState.putString("dateTimeTo_value",dateTimeTo);
        outState.putBoolean("toggleHistoryDisplayBtn_state", toggleBtnHistoryDisplay.isChecked());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void returnDates(String dates)
    {
        btnOpenCalendarDialog.setText("");
        btnOpenCalendarDialog.setText(dates);
    }
}