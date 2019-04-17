package com.example.app_v1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Temperature implements Serializable
{
    @SerializedName("temperature")
    @Expose
    private String temperature;

    @SerializedName("timestamp")
    @Expose
    private String dateTime;

    public Temperature(String temp, String dTime)
    {
        this.temperature = temp;
        this.dateTime = dTime;
    }

    /**
     *
     * @return
     * The temperature
     */
    public String getTemperature()
    {
        return this.temperature;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public String getDateTime()
    {
        return this.dateTime;
    }

    /**
     *
     * @set
     * The temperature
     */
    public void setTemperature(String temp)
    {
        this.temperature = temp;
    }

    /**
     *
     * @set
     * The timestamp
     */
    public void setDateTime(String dTime)
    {
        this.dateTime = dTime;
    }

    public String toString()
    {
        String s = String.format("Temperature: %s, time and date: %s",this.temperature,this.dateTime);

        return s;
    }
}