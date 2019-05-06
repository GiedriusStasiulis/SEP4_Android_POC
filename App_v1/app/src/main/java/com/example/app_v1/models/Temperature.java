package com.example.app_v1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Temperature implements Serializable
{
    private String tempValue;
    private String dateTime;

    public Temperature(String tempValue, String dateTime) {
        this.tempValue = tempValue;
        this.dateTime = dateTime;
    }

    public String getTempValue() {
        return tempValue;
    }

    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "tempValue='" + tempValue + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}