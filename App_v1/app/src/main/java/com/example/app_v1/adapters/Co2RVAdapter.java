package com.example.app_v1.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import com.example.app_v1.R;
import com.example.app_v1.models.Co2;
import com.example.app_v1.viewholders.Co2ViewHolder;

public class Co2RVAdapter extends GenericMeasurementRVAdapter<Co2, Co2ViewHolder>
{
    public Co2RVAdapter(Context context)
    {
        super(context);
    }

    @NonNull
    @Override
    public Co2ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new Co2ViewHolder(inflate(R.layout.rvitem_measurement,viewGroup));
    }
}
