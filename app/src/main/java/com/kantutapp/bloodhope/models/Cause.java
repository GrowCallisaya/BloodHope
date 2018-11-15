package com.kantutapp.bloodhope.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Cause implements Parcelable {
    public String key;
    public String title;
    public String image;
    public String deadline;
    public String description;
    public int number_donations;
    public int total_donations;
    public String blood_type;
    public String hospital_id;
    public String city_id;
    public String user_id;
    public String startdate;



    public Cause(String key, String title, String image, String deadline, String description, int number_donations, int total_donations, String blood_type, String hospital_id, String city_id, String user_id, String startdate) {
        this.key = key;
        this.title = title;
        this.image = image;
        this.deadline = deadline;
        this.description = description;
        this.number_donations = number_donations;
        this.total_donations = total_donations;
        this.blood_type = blood_type;
        this.hospital_id = hospital_id;
        this.city_id = city_id;
        this.user_id = user_id;
        this.startdate = startdate;
    }

    public Cause() {

    }

    protected Cause(Parcel in) {
        key= in.readString();
        title = in.readString();
        image = in.readString();
        deadline = in.readString();
        description = in.readString();
        number_donations = in.readInt();
        total_donations = in.readInt();
        blood_type = in.readString();
        hospital_id = in.readString();
        city_id = in.readString();
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
        dest.writeString(key);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(deadline);
        dest.writeString(description);
        dest.writeInt(number_donations);
        dest.writeInt(total_donations);
        dest.writeString(blood_type);
        dest.writeString(hospital_id);
        dest.writeString(city_id);
        dest.writeString(user_id);
        dest.writeString(startdate);
    }


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

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
