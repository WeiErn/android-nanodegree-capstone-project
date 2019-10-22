package com.udacity.findaflight.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.udacity.findaflight.database.Converter;

import java.util.List;

@Entity(tableName = "compact_result")
public class CompactResult implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(Converter.class)
    private List<String> airlines;
    @ColumnInfo(name="travel_period")
    private String travelPeriod;
    @ColumnInfo(name="outbound_start_time")
    private String outboundStartTime;
    @ColumnInfo(name="outbound_start_airport")
    private String outboundStartAirport;
    @ColumnInfo(name="outbound_end_time")
    private String outboundEndTime;
    @ColumnInfo(name="outbound_end_airport")
    private String outboundEndAirport;
    @ColumnInfo(name="inbound_end_time")
    private String inboundEndTime;
    @ColumnInfo(name="inbound_end_airport")
    private String inboundEndAirport;
    @ColumnInfo(name="inbound_start_time")
    private String inboundStartTime;
    @ColumnInfo(name="inbound_start_airport")
    private String inboundStartAirport;
    private int price;

    public int getId() {
        return id;
    }
    public List<String> getAirlines() {
        return airlines;
    }
    public String getTravelPeriod() {
        return travelPeriod;
    }
    public String getOutboundStartTime() {
        return outboundStartTime;
    }
    public String getOutboundStartAirport() {
        return outboundStartAirport;
    }
    public String getOutboundEndTime() {
        return outboundEndTime;
    }
    public String getOutboundEndAirport() {
        return outboundEndAirport;
    }
    public String getInboundEndTime() {
        return inboundEndTime;
    }
    public String getInboundEndAirport() {
        return inboundEndAirport;
    }
    public String getInboundStartTime() {
        return inboundStartTime;
    }
    public String getInboundStartAirport() {
        return inboundStartAirport;
    }
    public int getPrice() {
        return price;
    }

    @Ignore
    public CompactResult(List airlines, String travelPeriod, String outboundStartTime, String outboundStartAirport, String outboundEndTime, String outboundEndAirport, String inboundEndTime, String inboundEndAirport, String inboundStartTime, String inboundStartAirport, int price) {
        this.airlines = airlines;
        this.travelPeriod = travelPeriod;
        this.outboundStartTime = outboundStartTime;
        this.outboundStartAirport = outboundStartAirport;
        this.outboundEndTime = outboundEndTime;
        this.outboundEndAirport = outboundEndAirport;
        this.inboundEndTime = inboundEndTime;
        this.inboundEndAirport = inboundEndAirport;
        this.inboundStartTime = inboundStartTime;
        this.inboundStartAirport = inboundStartAirport;
        this.price = price;
    }

    public CompactResult(int id, List airlines, String travelPeriod, String outboundStartTime, String outboundStartAirport, String outboundEndTime, String outboundEndAirport, String inboundEndTime, String inboundEndAirport, String inboundStartTime, String inboundStartAirport, int price) {
        this.id = id;
        this.airlines = airlines;
        this.travelPeriod = travelPeriod;
        this.outboundStartTime = outboundStartTime;
        this.outboundStartAirport = outboundStartAirport;
        this.outboundEndTime = outboundEndTime;
        this.outboundEndAirport = outboundEndAirport;
        this.inboundEndTime = inboundEndTime;
        this.inboundEndAirport = inboundEndAirport;
        this.inboundStartTime = inboundStartTime;
        this.inboundStartAirport = inboundStartAirport;
        this.price = price;
    }

    protected CompactResult(Parcel in) {
        id = in.readInt();
        airlines = in.createStringArrayList();
        travelPeriod = in.readString();
        outboundStartTime = in.readString();
        outboundStartAirport = in.readString();
        outboundEndTime = in.readString();
        outboundEndAirport = in.readString();
        inboundEndTime = in.readString();
        inboundEndAirport = in.readString();
        inboundStartTime = in.readString();
        inboundStartAirport = in.readString();
        price = in.readInt();
    }

    public static final Creator<CompactResult> CREATOR = new Creator<CompactResult>() {
        @Override
        public CompactResult createFromParcel(Parcel in) {
            return new CompactResult(in);
        }

        @Override
        public CompactResult[] newArray(int size) {
            return new CompactResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeStringList(airlines);
        dest.writeString(travelPeriod);
        dest.writeString(outboundStartTime);
        dest.writeString(outboundStartAirport);
        dest.writeString(outboundEndTime);
        dest.writeString(outboundEndAirport);
        dest.writeString(inboundEndTime);
        dest.writeString(inboundEndAirport);
        dest.writeString(inboundStartTime);
        dest.writeString(inboundStartAirport);
        dest.writeInt(price);
    }
}
