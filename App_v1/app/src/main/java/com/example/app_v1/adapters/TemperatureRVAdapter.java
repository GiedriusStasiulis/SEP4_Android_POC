package com.example.app_v1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.example.app_v1.R;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.viewholders.TemperatureViewHolder;

public class TemperatureRVAdapter extends GenericMeasurementRVAdapter<Temperature, TemperatureViewHolder>
{
    public TemperatureRVAdapter(Context context)
    {
        super(context);
    }

    @NonNull
    @Override
    public TemperatureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new TemperatureViewHolder(inflate(R.layout.rvitem_measurement,viewGroup));
    }
}