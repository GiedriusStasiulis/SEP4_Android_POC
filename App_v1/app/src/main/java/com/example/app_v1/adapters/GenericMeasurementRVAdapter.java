package com.example.app_v1.adapters;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_v1.viewholders.BaseViewHolder;

import java.util.ArrayList;

public abstract class GenericMeasurementRVAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH>
{
    private ArrayList<T> items;
    private LayoutInflater layoutInflater;

    GenericMeasurementRVAdapter(Context context)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i)
    {
        T item = items.get(i);
        vh.onBind(item);
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public void setItems(ArrayList<T> items)
    {
        if(items == null)
        {
            throw new IllegalArgumentException("Cannot set `null` item to the Recycler adapter");
        }

        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems()
    {
        this.items.clear();
        notifyDataSetChanged();
        notifyItemRangeChanged(0,items.size());
    }

    @NonNull
    private View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot)
    {
        return layoutInflater.inflate(layout,parent,attachToRoot);
    }

    @NonNull
    View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent)
    {
        return inflate(layout,parent,false);
    }
}
