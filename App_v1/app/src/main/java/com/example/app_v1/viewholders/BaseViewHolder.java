package com.example.app_v1.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder
{
    public BaseViewHolder(@NonNull View itemView)
    {
        super(itemView);
    }

    public abstract void onBind(T item);
}
