package com.udacity.findaflight.data;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;

public class ParcelableArrayList extends ArrayList<String> implements Parcelable {

    public ParcelableArrayList() {
        super();
    }

    protected ParcelableArrayList(Parcel in) {
        in.readList(this, String.class.getClassLoader());
    }

    public static final Creator<ParcelableArrayList> CREATOR = new Creator<ParcelableArrayList>() {
        @Override
        public ParcelableArrayList createFromParcel(Parcel in) {
            return new ParcelableArrayList(in);
        }

        @Override
        public ParcelableArrayList[] newArray(int size) {
            return new ParcelableArrayList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }
}
