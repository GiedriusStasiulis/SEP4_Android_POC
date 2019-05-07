package com.example.app_v1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.app_v1.repositories.Repository;

public class MeasurementHistoryViewModel extends ViewModel
{
    private Repository repo;

    private MutableLiveData<Integer> selectedTabIndex = new MutableLiveData<>();

    public void initViewModel()
    {
        repo = Repository.getInstance();
        selectedTabIndex.setValue(0);
        repo.addDummyMeasurements();
    }

    public void setSelectedTabIndex(Integer index)
    {
        selectedTabIndex.postValue(index);
    }

    public MutableLiveData<Integer> getSelectedTabIndex()
    {
        return selectedTabIndex;
    }
}
