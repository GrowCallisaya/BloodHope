package com.kantutapp.bloodhope.models;

public class CauseResponse {
    String title;
    String image;
    String user_id;
    String blood_type;
    String description;
    int number_donations;
    String dealine;
    String hospital_id;
    String startdate;
    String city_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber_donations() {
        return number_donations;
    }

    public void setNumber_donations(int number_donations) {
        this.number_donations = number_donations;
    }

    public String getDealine() {
        return dealine;
    }

    public void setDealine(String dealine) {
        this.dealine = dealine;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
