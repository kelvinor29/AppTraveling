package com.kelvin.apptraveling.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GuestReviews implements Parcelable {
    private double unformattedRating;

    protected GuestReviews(Parcel in) {
        unformattedRating = in.readDouble();
    }

    public static final Creator<GuestReviews> CREATOR = new Creator<GuestReviews>() {
        @Override
        public GuestReviews createFromParcel(Parcel in) {
            return new GuestReviews(in);
        }

        @Override
        public GuestReviews[] newArray(int size) {
            return new GuestReviews[size];
        }
    };

    public String getUnformattedRating() {
        return String.valueOf(unformattedRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(unformattedRating);
    }
}
