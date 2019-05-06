package com.example.app_v1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Temperature implements Serializable
{
    private String temperature;
    private String time;
    private String date;

    public Temperature(String temperature)
    {
        this.temperature = temperature;
    }

    public Temperature(String temperature, String time, String date)
    {
        this.temperature = temperature;
        this.time = time;
        this.date = date;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Temperature{" +
                "temperature='" + temperature + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}