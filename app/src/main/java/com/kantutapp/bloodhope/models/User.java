package com.kantutapp.bloodhope.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String name;
    String photo;
    String phoneNumber;
    String typeOfBlood;

    public User() {
    }

    public User(  String name, String photo, String phoneNumber, String typeOfBlood) {

        this.name = name;
        this.photo = photo;
        this.phoneNumber = phoneNumber;
        this.typeOfBlood = typeOfBlood;
    }

    protected User(Parcel in) {
        name = in.readString();
        photo = in.readString();
        phoneNumber = in.readString();
        typeOfBlood = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypeOfBlood() {
        return typeOfBlood;
    }

    public void setTypeOfBlood(String typeOfBlood) {
        this.typeOfBlood = typeOfBlood;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(phoneNumber);
        dest.writeString(typeOfBlood);
    }
}
