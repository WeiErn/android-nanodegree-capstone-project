package com.udacity.findaflight.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Flight implements Parcelable {

    private String airline;
    private String departureStartTime;
    private String departureStartAirport;
    private String departureEndTime;
    private String departureEndAirport;
    private String returnStartTime;
    private String returnStartAirport;
    private String returnEndTime;
    private String returnEndAirport;
    private String price;

    public String getAirline() {
        return airline;
    }

    public String getDepartureStartTime() {
        return departureStartTime;
    }

    public String getDepartureStartAirport() {
        return departureStartAirport;
    }

    public String getDepartureEndTime() {
        return departureEndTime;
    }

    public String getDepartureEndAirport() {
        return departureEndAirport;
    }

    public String getReturnStartTime() {
        return returnStartTime;
    }

    public String getReturnStartAirport() {
        return returnStartAirport;
    }

    public String getReturnEndTime() {
        return returnEndTime;
    }

    public String getReturnEndAirport() {
        return returnEndAirport;
    }

    public String getPrice() {
        return price;
    }

    public Flight(String airline, String departureStartTime, String departureStartAirport, String departureEndTime, String departureEndAirport, String returnStartTime, String returnStartAirport, String returnEndTime, String returnEndAirport, String price) {
        this.airline = airline;
        this.departureStartTime = departureStartTime;
        this.departureStartAirport = departureStartAirport;
        this.departureEndTime = departureEndTime;
        this.departureEndAirport = departureEndAirport;
        this.returnStartTime = returnStartTime;
        this.returnStartAirport = returnStartAirport;
        this.returnEndTime = returnEndTime;
        this.returnEndAirport = returnEndAirport;
        this.price = price;
    }

    protected Flight(Parcel in) {
        airline = in.readString();
        departureStartTime = in.readString();
        departureStartAirport = in.readString();
        departureEndTime = in.readString();
        departureEndAirport = in.readString();
        returnStartTime = in.readString();
        returnStartAirport = in.readString();
        returnEndTime = in.readString();
        returnEndAirport = in.readString();
        price = in.readString();
    }

    public static final Creator<Flight> CREATOR = new Creator<Flight>() {
        @Override
        public Flight createFromParcel(Parcel source) {
            return new Flight(source);
        }

        @Override
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airline);
        dest.writeString(departureStartTime);
        dest.writeString(departureStartAirport);
        dest.writeString(departureEndTime);
        dest.writeString(departureEndAirport);
        dest.writeString(returnStartTime);
        dest.writeString(returnStartAirport);
        dest.writeString(returnEndTime);
        dest.writeString(returnEndAirport);
        dest.writeString(price);
    }
}
