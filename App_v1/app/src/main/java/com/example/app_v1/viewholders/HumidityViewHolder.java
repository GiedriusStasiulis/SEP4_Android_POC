package com.example.app_v1.viewholders;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.example.app_v1.R;
import com.example.app_v1.models.Humidity;

public class HumidityViewHolder extends BaseViewHolder<Humidity>
{
    private TextView measurementRVItemValue;
    private TextView measurementRVItemSymbol;
    private TextView measurementRVItemTime;
    private TextView measurementRVItemDate;

    public HumidityViewHolder(@NonNull View itemView)
    {
        super(itemView);
        measurementRVItemValue = itemView.findViewById(R.id.measurementRVItemValue);
        measurementRVItemSymbol = itemView.findViewById(R.id.measurementRVItemSymbol);
        measurementRVItemTime = itemView.findViewById(R.id.measurementRVItemTime);
        measurementRVItemDate = itemView.findViewById(R.id.measurementRVItemDate);
        measurementRVItemSymbol.setText(itemView.getResources().getString(R.string.symbol_humidity));
    }

    @Override
    public void onBind(Humidity item)
    {
        measurementRVItemValue.setText(item.getHumidity());
        measurementRVItemTime.setText(item.getTime());
        measurementRVItemDate.setText(item.getDate());
    }
}