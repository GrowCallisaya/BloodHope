package com.kantutapp.bloodhope.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class Collaborator {
    String id_cause;
    String id_user;
    Boolean status;

    public Collaborator() {
    }

    public Collaborator(String id_cause, String id_user, Boolean status) {
        this.id_cause = id_cause;
        this.id_user = id_user;
        this.status = status;
    }

    public String getId_cause() {
        return id_cause;
    }

    public void setId_cause(String id_cause) {
        this.id_cause = id_cause;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
