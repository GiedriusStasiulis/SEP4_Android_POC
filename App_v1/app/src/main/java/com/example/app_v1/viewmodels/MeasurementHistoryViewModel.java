package com.example.app_v1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.repositories.Repository;
import java.text.ParseException;
import java.util.ArrayList;

public class MeasurementHistoryViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();
    private ArrayList<Temperature> temperaturesInDateRange = new ArrayList<>();

    public void initViewModel()
    {
        repo = Repository.getInstance();
        selectedTabIndex.setValue(0);
    }

    public void setSelectedTabIndex(Integer index)
    {
        selectedTabIndex.postValue(index);
    }

    public MutableLiveData<Integer> getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public ArrayList<Temperature> getTemperaturesInDateRange(String timestampISO8601from, String timestampISO8601to) throws ParseException
    {
        temperaturesInDateRange.clear();
        temperaturesInDateRange = repo.getTemperaturesInDateRange(timestampISO8601from,timestampISO8601to);

        return this.temperaturesInDateRange;
    }
}
