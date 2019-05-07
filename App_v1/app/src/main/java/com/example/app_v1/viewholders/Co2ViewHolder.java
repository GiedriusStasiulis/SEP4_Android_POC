package com.example.app_v1.viewholders;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.example.app_v1.R;
import com.example.app_v1.models.Co2;

public class Co2ViewHolder extends BaseViewHolder<Co2>
{
    private TextView measurementRVItemValue;
    private TextView measurementRVItemSymbol;
    private TextView measurementRVItemTime;
    private TextView measurementRVItemDate;

    public Co2ViewHolder(@NonNull View itemView)
    {
        super(itemView);
        measurementRVItemValue = itemView.findViewById(R.id.measurementRVItemValue);
        measurementRVItemSymbol = itemView.findViewById(R.id.measurementRVItemSymbol);
        measurementRVItemTime = itemView.findViewById(R.id.measurementRVItemTime);
        measurementRVItemDate = itemView.findViewById(R.id.measurementRVItemDate);
        measurementRVItemSymbol.setText(itemView.getResources().getString(R.string.symbol_co2));
    }

    @Override
    public void onBind(Co2 item)
    {
        measurementRVItemValue.setText(item.getCo2());
        measurementRVItemTime.setText(item.getTime());
        measurementRVItemDate.setText(item.getDate());
    }
}
