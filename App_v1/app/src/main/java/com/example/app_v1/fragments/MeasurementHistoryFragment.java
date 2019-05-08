package com.example.app_v1.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_v1.R;
import com.example.app_v1.adapters.Co2RVAdapter;
import com.example.app_v1.adapters.HumidityRVAdapter;
import com.example.app_v1.adapters.TemperatureRVAdapter;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.utils.DTimeFormatHelper;
import com.example.app_v1.viewmodels.MeasurementHistoryViewModel;

import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MeasurementHistoryFragment extends Fragment
{
    private static final String TAG = "MeasurementHistoryFragment";

    private String dateFrom;
    private String timeFrom;
    private String dateTimeFrom;

    private String dateTo;
    private String timeTo;
    private String dateTimeTo;

    private TextView titleMeasurementType;

    private Button btnSelectDateTimeFrom;
    private Button btnSelectDateTimeTo;

    private ImageButton btnSearchHistory;

    private RecyclerView rvMeasurementHistory;
    private TemperatureRVAdapter temperatureRVAdapter;
    private HumidityRVAdapter humidityRVAdapter;
    private Co2RVAdapter co2RVAdapter;

    private ArrayList<Temperature> temperaturesInDateRange = new ArrayList<>();
    private ArrayList<Humidity> humidityInDateRange = new ArrayList<>();
    private ArrayList<Co2> co2InDateRange = new ArrayList<>();

    private DatePickerDialog.OnDateSetListener dateFromSetListener;
    private TimePickerDialog.OnTimeSetListener timeFromSetListener;
    private DatePickerDialog.OnDateSetListener dateToSetListener;
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
        btnSelectDateTimeFrom = view.findViewById(R.id.btnSelectDateTimeFrom);
        btnSelectDateTimeTo = view.findViewById(R.id.btnSelectDateTimeTo);
        btnSearchHistory = view.findViewById(R.id.btnSearchHistory);
        rvMeasurementHistory = view.findViewById(R.id.rvMeasurementHistory);

        dateTimeFrom = DTimeFormatHelper.getYesterdayDateTimeAsString();
        dateTimeTo = DTimeFormatHelper.getCurrentDateTimeAsString();

        btnSelectDateTimeFrom.setText(dateTimeFrom);
        btnSelectDateTimeTo.setText(dateTimeTo);

        btnSelectDateTimeFrom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateFromSetListener,
                        year,month,day);

                dateDialog.getDatePicker().setMaxDate(DTimeFormatHelper.getCurrentDateAsLong());
                dateDialog.getDatePicker().setMinDate(DTimeFormatHelper.getMinDateAsLong());

                //Add min date a month back max

                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }
        });

        dateFromSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                dateFrom = DTimeFormatHelper.convertDatePickerValuesToString(year,month,dayOfMonth);

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
        };

        timeFromSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                timeFrom = DTimeFormatHelper.convertTimePickerValuesToString(hourOfDay,minute);

                dateTimeFrom = String.format("%s %s",dateFrom,timeFrom);
                btnSelectDateTimeFrom.setText(dateTimeFrom);
            }
        };

        btnSelectDateTimeTo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateToSetListener,
                        year,month,day);

                dateDialog.getDatePicker().setMaxDate(DTimeFormatHelper.getCurrentDateAsLong());
                dateDialog.getDatePicker().setMinDate(DTimeFormatHelper.getMinDateAsLong());

                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }
        });

        dateToSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                dateTo = DTimeFormatHelper.convertDatePickerValuesToString(year,month,dayOfMonth);

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
        };

        timeToSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                timeTo = DTimeFormatHelper.convertTimePickerValuesToString(hourOfDay,minute);

                dateTimeTo = String.format("%s %s",dateTo,timeTo);
                btnSelectDateTimeTo.setText(dateTimeTo);
            }
        };

        measurementHistoryViewModel = ViewModelProviders.of(getActivity()).get(MeasurementHistoryViewModel.class);



        measurementHistoryViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                switch (integer)
                {
                    case 0:

                        initTemperatureHistoryRView();

                        try {
                            temperaturesInDateRange = measurementHistoryViewModel.getTemperaturesInDateRange(dateTimeFrom,dateTimeTo).getValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        temperatureRVAdapter.clearItems();
                        temperatureRVAdapter.setItems(temperaturesInDateRange);

                        titleMeasurementType.setText(getResources().getString(R.string.title_temperature));

                        btnSearchHistory.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                temperaturesInDateRange.clear();

                                    try {
                                        temperaturesInDateRange = measurementHistoryViewModel.getTemperaturesInDateRange(dateTimeFrom,dateTimeTo).getValue();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    temperatureRVAdapter.clearItems();
                                    temperatureRVAdapter.setItems(temperaturesInDateRange);
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
            btnSelectDateTimeFrom.setText(dateTimeFrom);
            btnSelectDateTimeTo.setText(dateTimeTo);
        }
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
        //outState.putParcelable("rvHistory_state", rvMeasurementHistory.getLayoutManager().onSaveInstanceState());
        //outState.putInt("rvAdapter_state",rvMeasurementHistory.getScrollState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume()
    {
        super.onResume();


    }
}