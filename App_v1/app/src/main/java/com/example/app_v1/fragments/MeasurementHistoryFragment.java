package com.example.app_v1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.app_v1.R;
import com.example.app_v1.viewmodels.MeasurementHistoryViewModel;

import org.w3c.dom.Text;

public class MeasurementHistoryFragment extends Fragment
{
    private static final String TAG = "MeasurementHistoryFragment";

    private String bundleContent;

    private TextView titleMeasurementType;

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

        measurementHistoryViewModel = ViewModelProviders.of(getActivity()).get(MeasurementHistoryViewModel.class);

        measurementHistoryViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                switch (integer)
                {
                    case 0:

                        titleMeasurementType.setText(getResources().getString(R.string.title_temperature));

                        break;

                    case 1:

                        titleMeasurementType.setText(getResources().getString(R.string.title_humidity));

                        break;

                    case 2:

                        titleMeasurementType.setText(getResources().getString(R.string.title_co2));

                        break;

                        default:
                            break;
                }
            }
        });


    }
}