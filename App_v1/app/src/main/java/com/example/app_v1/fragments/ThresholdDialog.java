package com.example.app_v1.fragments;


import android.content.Context;
import android.content.Intent;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.app_v1.R;
import com.example.app_v1.models.Threshold;
import com.example.app_v1.room.ThresholdDAO;
import com.example.app_v1.viewmodels.ThresholdViewModel;

import java.util.ArrayList;
import java.util.List;

public class ThresholdDialog extends DialogFragment {
    private static final String TAG = "ThresholdDialogFragment";

    private EditText mMinRange, mMaxRange;
    private TextView mActionOk, mActionCancel;
    private TextView tempMinValue;
    private TextView tempMaxValue;

    private ThresholdViewModel roomDbViewModel;

    public ThresholdDialog(TextView min, TextView max){
        this.tempMinValue = min;
        this.tempMaxValue = max;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_threshold, container, false);

        roomDbViewModel = ViewModelProviders.of(this).get(ThresholdViewModel.class);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        mMinRange = view.findViewById(R.id.minInput);
        mMinRange.setHint(tempMinValue.getText());
        mMaxRange = view.findViewById(R.id.maxInput);
        mMaxRange.setHint(tempMaxValue.getText());
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
                Log.d(TAG, "onClick: capturing input");
                if(!(mMinRange.getText().toString().isEmpty() || mMaxRange.getText().toString().isEmpty())){

                    Float inputMin = Float.parseFloat(mMinRange.getText().toString());
                    Float inputMax = Float.parseFloat(mMaxRange.getText().toString());

                    if ((inputMax >= 1 && inputMax <= 60) &&  (inputMin >= 1 && inputMin <= 60)) {
                        if(inputMin>inputMax){
                            Toast.makeText(getActivity(), "Maximum value must be greater than minimum value",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Values Saved",
                                    Toast.LENGTH_LONG).show();
                            Threshold updatedTemp = new Threshold("temperature", mMinRange.getText().toString(), mMaxRange.getText().toString());

                            updatedTemp.setId(1);
                            roomDbViewModel.update(updatedTemp);

                            getDialog().dismiss();
                        }
                    } else {

                        Toast.makeText(getActivity(), "VALUES NOT SAVED! \n Please insert values between 1 - 60",
                                Toast.LENGTH_LONG).show();

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