package com.kantutapp.bloodhope.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String name;
    String photo;
    String email;
    String phone_number;
    String blood_type;
    int number_donations;


    public User() {
    }

    public User(String name, String photo, String phone_number, String blood_type) {
        this.name = name;
        this.photo = photo;
        this.phone_number = phone_number;
        this.blood_type = blood_type;
    }

    protected User(Parcel in) {
        name = in.readString();
        photo = in.readString();
        phone_number = in.readString();
        blood_type = in.readString();
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(phone_number);
        dest.writeString(blood_type);
    }
}
