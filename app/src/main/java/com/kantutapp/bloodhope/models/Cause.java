package com.kantutapp.bloodhope.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class Cause implements Parcelable {
    @Getter @Setter String title;
    @Getter @Setter String image;
    @Getter @Setter String deadline;
    @Getter @Setter String description;
    @Getter @Setter int number_donations;
    @Getter @Setter int total_donations;
    @Getter @Setter String blood_type;
    @Getter @Setter String hospital;
    @Getter @Setter String city;
    @Getter @Setter String user_id;
    @Getter @Setter String startdate;



    public Cause(String title, String image, String deadline, String description, int number_donations, int total_donations, String blood_type, String hospital, String city, String user_id, String startdate) {
        this.title = title;
        this.image = image;
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

    protected Cause(Parcel in) {
        title = in.readString();
        image = in.readString();
        deadline = in.readString();
        description = in.readString();
        number_donations = in.readInt();
        total_donations = in.readInt();
        blood_type = in.readString();
        hospital = in.readString();
        city = in.readString();
        user_id = in.readString();
        startdate = in.readString();
    }

    public static final Creator<Cause> CREATOR = new Creator<Cause>() {
        @Override
        public Cause createFromParcel(Parcel in) {
            return new Cause(in);
        }

        @Override
        public Cause[] newArray(int size) {
            return new Cause[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(deadline);
        dest.writeString(description);
        dest.writeInt(number_donations);
        dest.writeInt(total_donations);
        dest.writeString(blood_type);
        dest.writeString(hospital);
        dest.writeString(city);
        dest.writeString(user_id);
        dest.writeString(startdate);
    }
}
