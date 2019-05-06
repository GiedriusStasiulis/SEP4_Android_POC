package com.example.app_v1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.sql.Timestamp;

public class Measurement implements Serializable
{
    @SerializedName("temperature")
    @Expose
    private Temperature temperature;

    @SerializedName("humidity")
    @Expose
    private Humidity humidity;

    @SerializedName("cO2")
    @Expose
    private Co2 cO2;

    @SerializedName("timeStamp")
    @Expose
    private Timestamp timeStamp;

    public Measurement(Temperature temperature, Humidity humidity, Co2 cO2, Timestamp timeStamp)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.cO2 = cO2;
        this.timeStamp = timeStamp;
    }

    public Temperature getTemperature()
    {
        return temperature;
    }

    public void setTemperature(Temperature temperature)
    {
        this.temperature = temperature;
    }

    public Humidity getHumidity()
    {
        return humidity;
    }

    public void setHumidity(Humidity humidity)
    {
        this.humidity = humidity;
    }

    public Co2 getcO2()
    {
        return cO2;
    }

    public void setcO2(Co2 cO2)
    {
        this.cO2 = cO2;
    }

    public Timestamp getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString()
    {
        return "Measurement{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", cO2=" + cO2 +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
