package com.example.app_v1.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app_v1.R;
import com.example.app_v1.viewmodels.MeasurementDetailsActivityViewModel;

public class MeasurementDetailsFragment extends Fragment
{
    private static final String TAG = "MeasurementDetailsFragment";

    private TextView txtView;
    private TextView selectedTab;

    private MeasurementDetailsActivityViewModel measurementDetailsActivityViewModel;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        measurementDetailsActivityViewModel = ViewModelProviders.of(this.getActivity()).get(MeasurementDetailsActivityViewModel.class);
        measurementDetailsActivityViewModel.getSelectedTabIndex().observe(this, new Observer<Integer>()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@Nullable Integer integer)
            {
                int tabPos = measurementDetailsActivityViewModel.getSelectedTabIndex().getValue();

                switch(tabPos)
                {
                    case 0:

                        selectedTab.setText("Temperature");

                        break;

                    case 1:

                        selectedTab.setText("Humidity");

                        break;

                    case 2:

                        selectedTab.setText("CO2");

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

        txtView = view.findViewById(R.id.measurementDetailsTxtView);
        selectedTab = view.findViewById(R.id.selectedTabTxtField);
        txtView.setText("Selected tab:");

        return view;
    }
}