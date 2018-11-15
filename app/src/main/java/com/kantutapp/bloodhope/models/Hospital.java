package com.kantutapp.bloodhope.models;

public class Hospital {
    String id_city;
    double latitutde;
    double longitude;
    String name;

    public Hospital() {
    }

    public Hospital(String id_city, double latitutde, double longitude, String name) {
        this.id_city = id_city;
        this.latitutde = latitutde;
        this.longitude = longitude;
        this.name = name;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
    }

    public double getLatitutde() {
        return latitutde;
    }

    public void setLatitutde(double latitutde) {
        this.latitutde = latitutde;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
