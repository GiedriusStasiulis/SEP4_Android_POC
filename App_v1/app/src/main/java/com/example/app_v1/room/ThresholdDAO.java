package com.example.app_v1.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app_v1.models.Threshold;

import java.util.List;

@Dao
public interface ThresholdDAO {
    @Insert
    void insert(Threshold threshold);
    @Update
    void update(Threshold threshold);
    @Delete
    void delete(Threshold threshold);

    @Query("DELETE FROM threshold_table")
    void deleteAllThresholds();

    @Query("SELECT * FROM threshold_table")
    LiveData<List<Threshold>> getAllThresholds();

   /* @Query("UPDATE threshold_table SET minValue= :minValue, maxValue= :maxValue WHERE id=1 ")
    void update(String minValue, String maxValue);*/


}
