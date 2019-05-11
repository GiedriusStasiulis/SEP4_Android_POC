package com.example.app_v1.fragments;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.app_v1.utils.DateTimeConverterHelper;
import com.example.app_v1.viewmodels.MeasurementHistoryViewModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MeasurementHistoryFragment extends Fragment implements DateRangePickerFragmentDialog.DateRangePickerFragmentDialogListener
{
    private static final String TAG = "MeasurementHistoryFragment";

    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private String dateFromToString;
    private String dateTimeFrom;
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

    private OnTimeSetListener timeFromSetListener;
    private OnTimeSetListener timeToSetListener;

    private MeasurementHistoryViewModel measurementHistoryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_measurement_history,container,false);
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

        btnOpenCalendarDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePickerDialog();
            }
        });

        btnSelectTimeFrom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timeDialog = new TimePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeFromSetListener,
                        hourOfDay,minute,true);

                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
            }
        });

        timeFromSetListener = new OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                timeFrom = DateTimeConverterHelper.convertTimePickerValuesToString(hourOfDay,minute);
                btnSelectTimeFrom.setText(timeFrom);
            }
        };

        btnSelectTimeTo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timeDialog = new TimePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeToSetListener,
                        hourOfDay,minute,true);

                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
            }
        });

        timeToSetListener = new OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                timeTo = DateTimeConverterHelper.convertTimePickerValuesToString(hourOfDay,minute);
                btnSelectTimeTo.setText(timeTo);
            }
        };

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

                        temperatureRVAdapter.clearItems();
                        temperatureRVAdapter.setItems(temperaturesInDateRange);

                        titleMeasurementType.setText(getResources().getString(R.string.title_temperature));

                        btnSearchHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                if(!measurementHistoryViewModel.validateSearchParameters(btnOpenCalendarDialog.getText().toString(),
                                        btnSelectTimeFrom.getText().toString(),
                                        btnSelectTimeTo.getText().toString()))
                                {
                                    Toast.makeText(getActivity(), "Please select all search parameters",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    temperaturesInDateRange.clear();

                                    dateTimeFrom = String.format(Locale.ENGLISH,"%s %s",dateFrom,timeFrom);
                                    dateTimeTo = String.format(Locale.ENGLISH,"%s %s",dateTo,timeTo);

                                    /*Toast.makeText(getActivity(), "Searching for humidity in date range: " + dateTimeFrom + " and " + dateTimeTo,
                                            Toast.LENGTH_SHORT).show();*/

                                    //Test to see ISO8601 timestamps that will be sent to repo with selectedGreenhouseId
                                    String dateTimeFromISO8601 = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeFrom);
                                    String dateTimeToISO8601 = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeTo);

                                    Toast.makeText(getActivity(), "Searching for temperature in date range: " + dateTimeFromISO8601 + " and " + dateTimeToISO8601,
                                            Toast.LENGTH_SHORT).show();

                                    temperaturesInDateRange = measurementHistoryViewModel.getTemperaturesInDateRange(dateTimeFrom,dateTimeTo).getValue();
                                    temperatureRVAdapter.clearItems();
                                    temperatureRVAdapter.setItems(temperaturesInDateRange);
                                }
                            }
                        });
                        break;

                    case 1:

                        initHumidityHistoryRView();

                        //humidityInDateRange = measurementHistoryViewModel.getHumidityInDateRange(dateTimeFrom,dateTimeTo);

                        humidityRVAdapter.clearItems();
                        humidityRVAdapter.setItems(humidityInDateRange);

                        titleMeasurementType.setText(getResources().getString(R.string.title_humidity));

                        btnSearchHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                if(!measurementHistoryViewModel.validateSearchParameters(btnOpenCalendarDialog.getText().toString(),
                                        btnSelectTimeFrom.getText().toString(),
                                        btnSelectTimeTo.getText().toString()))
                                {
                                    Toast.makeText(getActivity(), "Please select all search parameters",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Searching...",
                                            Toast.LENGTH_SHORT).show();

                                    humidityInDateRange.clear();

                                    dateTimeFrom = String.format(Locale.ENGLISH,"%s %s",dateFrom,timeFrom);
                                    dateTimeTo = String.format(Locale.ENGLISH,"%s %s",dateTo,timeTo);

                                    /*Toast.makeText(getActivity(), "Searching for humidity in date range: " + dateTimeFrom + " and " + dateTimeTo,
                                            Toast.LENGTH_SHORT).show();*/

                                    //Test to see ISO8601 timestamp format that will be sent to repo
                                    String dateTimeFromISO8601 = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeFrom);
                                    String dateTimeToISO8601 = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeTo);

                                    Toast.makeText(getActivity(), "Searching for humidity in date range: " + dateTimeFromISO8601 + " and " + dateTimeToISO8601,
                                            Toast.LENGTH_SHORT).show();

                                    humidityInDateRange = measurementHistoryViewModel.getHumidityInDateRange(dateTimeFrom,dateTimeTo);
                                    humidityRVAdapter.clearItems();
                                    humidityRVAdapter.setItems(humidityInDateRange);
                                }
                            }
                        });
                        break;

                    case 2:

                        initCo2HistoryRView();

                        //co2InDateRange = measurementHistoryViewModel.getCo2InDateRange(dateTimeFrom,dateTimeTo);

                        co2RVAdapter.clearItems();
                        co2RVAdapter.setItems(co2InDateRange);

                        titleMeasurementType.setText(getResources().getString(R.string.title_co2));

                        btnSearchHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                if(!measurementHistoryViewModel.validateSearchParameters(btnOpenCalendarDialog.getText().toString(),
                                        btnSelectTimeFrom.getText().toString(),
                                        btnSelectTimeTo.getText().toString()))
                                {
                                    Toast.makeText(getActivity(), "Please select all search parameters",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Searching...",
                                            Toast.LENGTH_SHORT).show();

                                    co2InDateRange.clear();

                                    dateTimeFrom = String.format(Locale.ENGLISH,"%s %s",dateFrom,timeFrom);
                                    dateTimeTo = String.format(Locale.ENGLISH,"%s %s",dateTo,timeTo);

                                    //Test to see ISO8601 timestamp format that will be sent to repo
                                    String dateTimeFromISO8601 = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeFrom);
                                    String dateTimeToISO8601 = DateTimeConverterHelper.convertDateTimeStringToISO8601String(dateTimeTo);

                                    Toast.makeText(getActivity(), "Searching for co2 in date range: " + dateTimeFromISO8601 + " and " + dateTimeToISO8601,
                                            Toast.LENGTH_SHORT).show();

                                    co2InDateRange = measurementHistoryViewModel.getCo2InDateRange(dateTimeFrom,dateTimeTo);
                                    co2RVAdapter.clearItems();
                                    co2RVAdapter.setItems(co2InDateRange);
                                }
                            }
                        });
                        break;

                        default:
                            break;
                }
            }
        });

        btnClearSearchParams.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnOpenCalendarDialog.setText(getResources().getString(R.string.title_select));
                btnSelectTimeFrom.setText(getResources().getString(R.string.title_select));
                btnSelectTimeTo.setText(getResources().getString(R.string.title_select));
            }
        });

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

        if(savedInstanceState != null)
        {
            dateFromToString = savedInstanceState.getString("dateFromTo_value");
            timeFrom = savedInstanceState.getString("timeFrom_value");
            timeTo = savedInstanceState.getString("timeTo_value");
            dateFrom = savedInstanceState.getString("dateFrom_value");
            dateTo = savedInstanceState.getString("dateTo_value");
            dateTimeFrom = savedInstanceState.getString("dateTimeFrom_value");
            dateTimeTo = savedInstanceState.getString("dateTimeTo_value");

            btnOpenCalendarDialog.setText(dateFromToString);
            btnSelectTimeFrom.setText(timeFrom);
            btnSelectTimeTo.setText(timeTo);

            toggleBtnHistoryDisplay.setChecked(savedInstanceState.getBoolean("toggleHistoryDisplayBtn_state"));
        }
    }

    private void openDatePickerDialog()
    {
        DateRangePickerFragmentDialog dateRangePickerFragmentDialog = new DateRangePickerFragmentDialog();
        dateRangePickerFragmentDialog.setTargetFragment(MeasurementHistoryFragment.this,1);
        dateRangePickerFragmentDialog.show(getFragmentManager(),null);
    }

    @Override
    public void returnDates(String dates)
    {
        String[] dateElements = dates.split(" - ");
        dateFrom = dateElements[0];
        dateTo = dateElements[1];

        btnOpenCalendarDialog.setText("");
        btnOpenCalendarDialog.setText(dates);
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
        outState.putString("dateFromTo_value",btnOpenCalendarDialog.getText().toString());
        outState.putString("dateTimeFrom_value",dateTimeFrom);
        outState.putString("dateTimeTo_value",dateTimeTo);
        outState.putString("dateFrom_value",dateFrom);
        outState.putString("dateTo_value",dateTo);
        outState.putString("timeFrom_value",btnSelectTimeFrom.getText().toString());
        outState.putString("timeTo_value",btnSelectTimeTo.getText().toString());
        outState.putBoolean("toggleHistoryDisplayBtn_state", toggleBtnHistoryDisplay.isChecked());
        super.onSaveInstanceState(outState);
    }
}