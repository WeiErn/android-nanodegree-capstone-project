package com.udacity.findaflight.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Airport implements Parcelable {

    private String iataCode;
    private String country;

    public Airport(String iataCode, String country) {
        this.iataCode = iataCode;
        this.country = country;
    }

    protected Airport(Parcel in) {
        iataCode = in.readString();
        country = in.readString();
    }

    public static final Creator<Airport> CREATOR = new Creator<Airport>() {
        @Override
        public Airport createFromParcel(Parcel in) {
            return new Airport(in);
        }

        @Override
        public Airport[] newArray(int size) {
            return new Airport[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iataCode);
        dest.writeString(country);
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getCountry() {
        return country;
    }
}
