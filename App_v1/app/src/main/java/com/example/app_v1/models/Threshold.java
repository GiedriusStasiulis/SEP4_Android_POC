package com.example.app_v1.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "threshold_table")
public class Threshold {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String thresholdName;

    private String minValue;

    private String maxValue;

    public Threshold(String thresholdName, String minValue, String maxValue) {
        this.thresholdName = thresholdName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getThresholdName() {
        return thresholdName;
    }

    public String getMinValue() {
        return minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }
}
