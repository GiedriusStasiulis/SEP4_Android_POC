package com.example.app_v1.viewholders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.app_v1.R;
import com.example.app_v1.models.Temperature;

public class TemperatureViewHolder extends BaseViewHolder<Temperature>
{
    private TextView measurementRVItemValue;
    private TextView measurementRVItemSymbol;
    private TextView measurementRVItemTimestamp;

    public TemperatureViewHolder(@NonNull View itemView)
    {
        super(itemView);
        measurementRVItemValue = itemView.findViewById(R.id.measurementRVItemValue);
        measurementRVItemSymbol = itemView.findViewById(R.id.measurementRVItemSymbol);
        measurementRVItemTimestamp = itemView.findViewById(R.id.measurementRVItemTimestamp);

        measurementRVItemSymbol.setText(itemView.getResources().getString(R.string.symbol_temperature));
    }

    @Override
    public void onBind(Temperature item)
    {
        measurementRVItemValue.setText(item.getTemperature());
        measurementRVItemTimestamp.setText(item.getDateTime());
    }
}
