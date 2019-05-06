package com.example.app_v1.models;

import android.support.annotation.NonNull;

public class Co2
{
    private String co2;
    private String time;
    private String date;

    public Co2(String co2)
    {
        this.co2 = co2;
    }

    public Co2(String co2, String time, String date)
    {
        this.co2 = co2;
        this.time = time;
        this.date = date;
    }

    public String getCo2()
    {
        return co2;
    }

    public void setCo2(String co2)
    {
        this.co2 = co2;
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
        return "Co2{" +
                "co2='" + co2 + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
