package com.kantutapp.bloodhope.models;

public class Cause {
    String name;
    String url;
    String donations;

    public Cause(String name, String url, String donations) {
        this.name = name;
        this.url = url;
        this.donations = donations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDonations() {
        return donations;
    }

    public void setDonations(String donations) {
        this.donations = donations;
    }
}
