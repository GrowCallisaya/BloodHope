package com.kantutapp.bloodhope.models;

public class UserCollaborator {
    String key;
    String photo;
    String name;
    String city;
    Boolean status;

    User user;
    Cause cause;

    public UserCollaborator() {
    }


    public UserCollaborator(String key, String photo, String name, String city, Boolean status) {
        this.key = key;
        this.photo = photo;
        this.name = name;
        this.city = city;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }
}
