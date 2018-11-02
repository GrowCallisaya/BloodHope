package com.kantutapp.bloodhope.models;

public class Cause {
    String title;
    String url;
    String deadline;
    String description;
    int number_donations;
    int total_donations;
    String blood_type;
    String hospital;
    String city;
    String user_id;
    String startdate;



    public Cause(String title, String url, String deadline, String description, int number_donations, int total_donations, String blood_type, String hospital, String city, String user_id, String startdate) {
        this.title = title;
        this.url = url;
        this.deadline = deadline;
        this.description = description;
        this.number_donations = number_donations;
        this.total_donations = total_donations;
        this.blood_type = blood_type;
        this.hospital = hospital;
        this.city = city;
        this.user_id = user_id;
        this.startdate = startdate;
    }

    public Cause() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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

    public int getTotal_donations() {
        return total_donations;
    }

    public void setTotal_donations(int total_donations) {
        this.total_donations = total_donations;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
}
