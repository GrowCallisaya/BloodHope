package com.kantutapp.bloodhope.models;

public class UserCollaborator {
    String photo;
    String name;
    String city;
    Boolean status;

    public UserCollaborator() {
    }


    public UserCollaborator(String photo, String name, String city, Boolean status) {
        this.photo = photo;
        this.name = name;
        this.city = city;
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
