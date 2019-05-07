package com.example.app_v1.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import com.example.app_v1.R;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.viewholders.HumidityViewHolder;

public class HumidityRVAdapter extends GenericMeasurementRVAdapter<Humidity, HumidityViewHolder>
{
    public HumidityRVAdapter(Context context)
    {
        super(context);
    }

    @NonNull
    @Override
    public HumidityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new HumidityViewHolder(inflate(R.layout.rvitem_measurement,viewGroup));
    }
}
