package com.example.app_v1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.example.app_v1.viewmodels.MeasurementHistoryViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MeasurementHistoryFragment extends Fragment
{
    private static final String TAG = "MeasurementHistoryFragment";

    private TextView titleMeasurementType;

    private Button btnSelectDateTimeFrom;
    private Button btnSelectDateTimeTo;

    private ImageButton btnSearchHistory;

    private RecyclerView rvMeasurementHistory;
    private TemperatureRVAdapter temperatureRVAdapter;
    private HumidityRVAdapter humidityRVAdapter;
    private Co2RVAdapter co2RVAdapter;

    private ArrayList<Temperature> recentTemperatures;
    private ArrayList<Humidity> recentHumiditys;
    private ArrayList<Co2> recentCo2s;

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

        btnSelectDateTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Select datetime from",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnSelectDateTimeTo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Select datetime to",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnSearchHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "Search!",
                        Toast.LENGTH_LONG).show();
            }
        });

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

                        titleMeasurementType.setText(getResources().getString(R.string.title_temperature));

                        break;

                    case 1:

                        initHumidityHistoryRView();

                        titleMeasurementType.setText(getResources().getString(R.string.title_humidity));

                        break;

                    case 2:

                        initCo2HistoryRView();

                        titleMeasurementType.setText(getResources().getString(R.string.title_co2));

                        break;

                        default:
                            break;
                }
            }
        });
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
}