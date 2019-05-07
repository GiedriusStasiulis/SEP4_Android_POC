package com.example.app_v1.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Greenhouse implements Serializable
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("postalCode")
    @Expose
    private String postalCode;

    @SerializedName("address")
    @Expose
    private String address;

    public Greenhouse(int id, String country, String city, String postalCode, String address) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    @Override
    public String toString() {
        return "Greenhouse {" +
                "id=" + id + '\'' +
                ", country=" + country + '\'' +
                ", city=" + city + '\'' +
                ", postalCode=" + postalCode + '\'' +
                ", address=" + address + '\'' +
                '}';
    }
}
