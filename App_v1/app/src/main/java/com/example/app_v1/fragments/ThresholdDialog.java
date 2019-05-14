package com.example.app_v1.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.app_v1.R;
import com.example.app_v1.models.Threshold;

import com.example.app_v1.models.ThresholdInterval;
import com.example.app_v1.viewmodels.DashboardActivityViewModel;

public class ThresholdDialog extends DialogFragment {
    private static final String TAG = "ThresholdDialogFragment";

    private EditText mMinRange, mMaxRange;
    private TextView mActionOk, mActionCancel;
    private TextView minValue;
    private TextView maxValue;

    private int tabIndex;
    private Threshold updatedTemp;
    private ThresholdInterval thIn;
    Float inputMin;
    Float inputMax;
    private DashboardActivityViewModel dashboardRoomDbViewModel;

    public ThresholdDialog(TextView min, TextView max, int tabIndex) {
        this.minValue = min;
        this.maxValue = max;
        this.tabIndex = tabIndex;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_threshold, container, false);

        dashboardRoomDbViewModel = ViewModelProviders.of(this).get(DashboardActivityViewModel.class);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        mMinRange = view.findViewById(R.id.minInput);
        mMinRange.setHint(minValue.getText());
        mMaxRange = view.findViewById(R.id.maxInput);
        mMaxRange.setHint(maxValue.getText());
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mMinRange.getText().toString().isEmpty() || mMaxRange.getText().toString().isEmpty())) {
                    inputMin = Float.parseFloat(mMinRange.getText().toString());
                    inputMax = Float.parseFloat(mMaxRange.getText().toString());
                    if (inputMin > inputMax) {
                        Toast.makeText(getActivity(), "Maximum value must be greater than minimum value",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                    switch (tabIndex)
                    {
                        case 0:
                            Log.d(TAG, "onClick: capturing input");


                            if ((inputMax >= 1 && inputMax <= 60) && (inputMin >= 1 && inputMin <= 60)) {

                                updatedTemp = new Threshold("temperature", mMinRange.getText().toString(), mMaxRange.getText().toString());

                                updatedTemp.setId(1);
                                dashboardRoomDbViewModel.update(updatedTemp);
                                thIn = new ThresholdInterval(inputMin, inputMax);
                                dashboardRoomDbViewModel.sendThresholdToApi(1, updatedTemp.getThresholdName(), thIn);
                                Toast.makeText(getActivity(), "Temperature threshold saved",
                                        Toast.LENGTH_LONG).show();
                                getDialog().dismiss();

                            } else {

                                Toast.makeText(getActivity(), "VALUES NOT SAVED! \n Please insert values between 1 - 60",
                                        Toast.LENGTH_LONG).show();
                            }

                            break;
                        case 1:

                            updatedTemp = new Threshold("humidity", mMinRange.getText().toString(), mMaxRange.getText().toString());

                            updatedTemp.setId(2);
                            dashboardRoomDbViewModel.update(updatedTemp);
                            thIn = new ThresholdInterval(inputMin, inputMax);

                            dashboardRoomDbViewModel.sendThresholdToApi(1, updatedTemp.getThresholdName(), thIn);
                            Toast.makeText(getActivity(), "Humidity threshold saved",
                                    Toast.LENGTH_LONG).show();
                            getDialog().dismiss();
                            break;
                        case 2:
                            updatedTemp = new Threshold("co2", mMinRange.getText().toString(), mMaxRange.getText().toString());

                            updatedTemp.setId(3);
                            dashboardRoomDbViewModel.update(updatedTemp);
                            thIn = new ThresholdInterval(inputMin, inputMax);
                            dashboardRoomDbViewModel.sendThresholdToApi(1, updatedTemp.getThresholdName(), thIn);
                            Toast.makeText(getActivity(), "Co2 threshold Saved",
                                    Toast.LENGTH_LONG).show();
                            getDialog().dismiss();
                            break;
                        default:
                            break;
                    }

                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please insert all values",
                            Toast.LENGTH_LONG).show();

                }

            }


        });
        return view;
    }

}