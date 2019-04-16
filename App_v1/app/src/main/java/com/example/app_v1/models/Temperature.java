package com.example.app_v1.models;

import java.sql.Timestamp;

public class Temperature
{
    private float temperature;
    private Timestamp dateTime;

    public Temperature(float temp, Timestamp dTime)
    {
        this.temperature = temp;
        this.dateTime = dTime;
    }

    public float getTemperature()
    {
        return this.temperature;
    }

    public Timestamp getDateTime()
    {
        return this.dateTime;
    }

    public String toString()
    {
        String s = String.format("Temperature: %.2f, time and date: %s",this.temperature,this.dateTime);

        return s;
    }
}