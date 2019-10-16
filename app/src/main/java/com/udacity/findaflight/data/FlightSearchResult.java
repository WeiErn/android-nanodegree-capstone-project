package com.udacity.findaflight.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

import static com.udacity.findaflight.utils.DateUtils.getDateTimeString;

public class FlightSearchResult implements Parcelable {

    private String[] flightIds;
    private int price;
    private List<FlightRoute> outboundFlightRoutes;
    private List<FlightRoute> inboundFlightRoutes;
    private List<String> airlines;
    private List<String> transfers;
    private boolean hasAirportChange;
    private Date departureDateTime;
    private Date arrivalDateTime;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private String departureCity;
    private String arrivalCity;
    private String departureCountryCode;
    private String departureCountryName;
    private String arrivalCountryCode;
    private String arrivalCountryName;
    private List<ParcelableArrayList> routes;
    private String outboundFlightDuration;
    private String inboundFlightDuration;
    private String link;

    public FlightSearchResult(String[] flightIds, int price, List<FlightRoute> outboundFlightRoutes, List<FlightRoute> inboundFlightRoutes, List<String> airlines, List<String> transfers, boolean hasAirportChange, Date departureDateTime, Date arrivalDateTime, String departureAirport, String arrivalAirport, String departureCity, String arrivalCity, String departureCountryCode, String departureCountryName, String arrivalCountryCode, String arrivalCountryName, List<ParcelableArrayList> routes, String outboundFlightDuration, String inboundFlightDuration, String link) {
        this.flightIds = flightIds;
        this.price = price;
        this.outboundFlightRoutes = outboundFlightRoutes;
        this.inboundFlightRoutes = inboundFlightRoutes;
        this.airlines = airlines;
        this.transfers = transfers;
        this.hasAirportChange = hasAirportChange;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        splitDateTime();
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureCountryCode = departureCountryCode;
        this.departureCountryName = departureCountryName;
        this.arrivalCountryCode = arrivalCountryCode;
        this.arrivalCountryName = arrivalCountryName;
        this.routes = routes;
        this.outboundFlightDuration = outboundFlightDuration;
        this.inboundFlightDuration = inboundFlightDuration;
        this.link = link;
    }

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

    protected FlightSearchResult(Parcel in) {
        flightIds = in.createStringArray();
        price = in.readInt();
        outboundFlightRoutes = in.createTypedArrayList(FlightRoute.CREATOR);
        inboundFlightRoutes = in.createTypedArrayList(FlightRoute.CREATOR);
        airlines = in.createStringArrayList();
        transfers = in.createStringArrayList();
        hasAirportChange = in.readInt() == 1;
        departureDateTime = new Date(in.readLong());
        arrivalDateTime = new Date(in.readLong());
        departureDate = in.readString();
        departureTime = in.readString();
        arrivalDate = in.readString();
        arrivalTime = in.readString();
        departureAirport = in.readString();
        arrivalAirport = in.readString();
        departureCity = in.readString();
        arrivalCity = in.readString();
        departureCountryCode = in.readString();
        departureCountryName = in.readString();
        arrivalCountryCode = in.readString();
        arrivalCountryName = in.readString();
        routes = in.createTypedArrayList(ParcelableArrayList.CREATOR);
        outboundFlightDuration = in.readString();
        inboundFlightDuration = in.readString();
        link = in.readString();
    }

    public static final Creator<FlightSearchResult> CREATOR = new Creator<FlightSearchResult>() {
        @Override
        public FlightSearchResult createFromParcel(Parcel source) {
            return new FlightSearchResult(source);
        }

        @Override
        public FlightSearchResult[] newArray(int size) {
            return new FlightSearchResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(flightIds);
        dest.writeInt(price);
        dest.writeTypedList(outboundFlightRoutes);
        dest.writeTypedList(inboundFlightRoutes);
        dest.writeStringList(airlines);
        dest.writeStringList(transfers);
        dest.writeInt(hasAirportChange ? 1 : 0);
        dest.writeLong(departureDateTime.getTime());
        dest.writeLong(arrivalDateTime.getTime());
        dest.writeString(departureDate);
        dest.writeString(departureTime);
        dest.writeString(arrivalDate);
        dest.writeString(arrivalTime);
        dest.writeString(departureAirport);
        dest.writeString(arrivalAirport);
        dest.writeString(departureCity);
        dest.writeString(arrivalCity);
        dest.writeString(departureCountryCode);
        dest.writeString(departureCountryName);
        dest.writeString(arrivalCountryCode);
        dest.writeString(arrivalCountryName);
        dest.writeList(routes);
        dest.writeString(outboundFlightDuration);
        dest.writeString(inboundFlightDuration);
        dest.writeString(link);
    }

    public String[] getFlightIds() {
        return flightIds;
    }

    public int getPrice() {
        return price;
    }

    public List<FlightRoute> getOutboundFlightRoutes() {
        return outboundFlightRoutes;
    }

    public List<FlightRoute> getInboundFlightRoutes() {
        return inboundFlightRoutes;
    }

    public List<String> getAirlines() {
        return airlines;
    }

    public List<String> getTransfers() {
        return transfers;
    }

    public boolean isHasAirportChange() {
        return hasAirportChange;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDepartureCountryCode() {
        return departureCountryCode;
    }

    public String getDepartureCountryName() {
        return departureCountryName;
    }

    public String getArrivalCountryCode() {
        return arrivalCountryCode;
    }

    public String getArrivalCountryName() {
        return arrivalCountryName;
    }

    public List<ParcelableArrayList> getRoutes() {
        return routes;
    }

    public String getOutboundFlightDuration() {
        return outboundFlightDuration;
    }

    public String getInboundFlightDuration() {
        return inboundFlightDuration;
    }

    public String getLink() {
        return link;
    }
}
