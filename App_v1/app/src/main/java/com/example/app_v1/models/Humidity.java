package com.example.app_v1.models;

import android.support.annotation.NonNull;

public class Humidity
{
    private String humidity;
    private String time;
    private String date;

    public Humidity(String humidity)
    {
        this.humidity = humidity;
    }

    public Humidity(String humidity, String time, String date)
    {
        this.humidity = humidity;
        this.time = time;
        this.date = date;
    }

    public String getHumidity()
    {
        return humidity;
    }

    public void setHumidity(String humidity)
    {
        this.humidity = humidity;
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

    @NonNull
    @Override
    public String toString()
    {
        return "Humidity{" +
                "humidity='" + humidity + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
