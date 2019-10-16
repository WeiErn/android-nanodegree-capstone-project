package com.udacity.findaflight.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import static com.udacity.findaflight.utils.DateUtils.getDateTimeString;

public class FlightRoute implements Parcelable {

    private String id;
    private Date departureDateTime;
    private Date arrivalDateTime;
    private String departureDate;
    private String arrivalDate;
    private String departureTime;
    private String arrivalTime;

    private String departureCity;
    private String arrivalCity;
    private String departureAirport;
    private String arrivalAirport;
    private String airline;
    private String operatingCarrier;
    private int flightNum;
    private String operatingFlightNum;

    private String fareClasses;
    private String fareBasis;
    private String fareFamily;
    private String fareCategory;

    private boolean isReturnRoute;

    public FlightRoute(String id, Date departureDateTime, Date arrivalDateTime, String departureCity, String arrivalCity, String departureAirport, String arrivalAirport, String airline, String operatingCarrier, int flightNum, String operatingFlightNum, String fareClasses, String fareBasis, String fareFamily, String fareCategory, boolean isReturnRoute) {
        this.id = id;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        splitDateTime();
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.airline = airline;
        this.operatingCarrier = operatingCarrier;
        this.flightNum = flightNum;
        this.operatingFlightNum = operatingFlightNum;
        this.fareClasses = fareClasses;
        this.fareBasis = fareBasis;
        this.fareFamily = fareFamily;
        this.fareCategory = fareCategory;
        this.isReturnRoute = isReturnRoute;
    }

    protected FlightRoute(Parcel in) {
        id = in.readString();
        departureDateTime = new Date(in.readLong());
        arrivalDateTime = new Date(in.readLong());
        departureDate = in.readString();
        arrivalDate = in.readString();
        departureTime = in.readString();
        arrivalTime = in.readString();
        departureCity = in.readString();
        arrivalCity = in.readString();
        departureAirport = in.readString();
        arrivalAirport = in.readString();
        airline = in.readString();
        operatingCarrier = in.readString();
        flightNum = in.readInt();
        operatingFlightNum = in.readString();
        fareClasses = in.readString();
        fareBasis = in.readString();
        fareFamily = in.readString();
        fareCategory = in.readString();
        isReturnRoute = in.readInt() == 1;
    }

    public static final Creator<FlightRoute> CREATOR = new Creator<FlightRoute>() {
        @Override
        public FlightRoute createFromParcel(Parcel in) {
            return new FlightRoute(in);
        }

        @Override
        public FlightRoute[] newArray(int size) {
            return new FlightRoute[size];
        }
    };

    private void splitDateTime() {
        String departureDateTimeString = getDateTimeString(departureDateTime);
        String[] departureDateTimeArray = departureDateTimeString.split("\\|");
        departureDate = departureDateTimeArray[0];
        departureTime = departureDateTimeArray[1];

        String arrivalDateTimeString = getDateTimeString(arrivalDateTime);
        String[] arrivalDateTimeArray = arrivalDateTimeString.split("\\|");
        arrivalDate = arrivalDateTimeArray[0];
        arrivalTime = arrivalDateTimeArray[1];
    }

    public String getId() {
        return id;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getAirline() {
        return airline;
    }

    public String getOperatingCarrier() {
        return operatingCarrier;
    }

    public int getFlightNum() {
        return flightNum;
    }

    public String getOperatingFlightNum() {
        return operatingFlightNum;
    }

    public String getFareClasses() {
        return fareClasses;
    }

    public String getFareBasis() {
        return fareBasis;
    }

    public String getFareFamily() {
        return fareFamily;
    }

    public String getFareCategory() {
        return fareCategory;
    }

    public boolean isReturnRoute() {
        return isReturnRoute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(departureDateTime.getTime());
        dest.writeLong(arrivalDateTime.getTime());
        dest.writeString(departureDate);
        dest.writeString(arrivalDate);
        dest.writeString(departureTime);
        dest.writeString(arrivalTime);
        dest.writeString(departureCity);
        dest.writeString(arrivalCity);
        dest.writeString(departureAirport);
        dest.writeString(arrivalAirport);
        dest.writeString(airline);
        dest.writeString(operatingCarrier);
        dest.writeInt(flightNum);
        dest.writeString(operatingFlightNum);
        dest.writeString(fareClasses);
        dest.writeString(fareBasis);
        dest.writeString(fareFamily);
        dest.writeString(fareCategory);
        dest.writeInt(isReturnRoute ? 1 : 0);
    }
}
