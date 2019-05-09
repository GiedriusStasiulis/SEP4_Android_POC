package com.example.app_v1.models;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Measurement implements Serializable
{
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    @SerializedName("temperature")
    @Expose
    private Double temperature;

    @SerializedName("humidity")
    @Expose
    private Double humidity;

    @SerializedName("cO2")
    @Expose
    private Double cO2;

    public Measurement(String timeStamp, Double temperature, Double humidity, Double cO2) {
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.cO2 = cO2;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getcO2() {
        return cO2;
    }

    public void setcO2(Double cO2) {
        this.cO2 = cO2;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "timeStamp='" + timeStamp + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", cO2=" + cO2 +
                '}';
    }
}
