package com.example.app_v1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.sql.Timestamp;

public class Measurement implements Serializable
{
    @SerializedName("temperature")
    @Expose
    private String temperature;

    @SerializedName("humidity")
    @Expose
    private String humidity;

    @SerializedName("cO2")
    @Expose
    private String cO2;

    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    public Measurement(String temperature, String humidity, String cO2, String timeStamp)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.cO2 = cO2;
        this.timeStamp = timeStamp;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public String getHumidity()
    {
        return humidity;
    }

    public void setHumidity(String humidity)
    {
        this.humidity = humidity;
    }

    public String getcO2()
    {
        return cO2;
    }

    public void setcO2(String cO2)
    {
        this.cO2 = cO2;
    }

    public String getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString()
    {
        return "Measurement{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", cO2='" + cO2 + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
