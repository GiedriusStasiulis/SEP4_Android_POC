package com.example.app_v1.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app_v1.R;

public class HumidityFragment extends Fragment
{
    private static final String TAG = "HumidityFragment";

    private TextView txtView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.humidity_fragment,container,false);

        txtView = (TextView) view.findViewById(R.id.humidityTextView);

        return view;
    }
}
