package com.kelvin.apptraveling.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RatePlan implements Parcelable {
    private Price price;

    protected RatePlan(Parcel in) {
    }

    public static final Creator<RatePlan> CREATOR = new Creator<RatePlan>() {
        @Override
        public RatePlan createFromParcel(Parcel in) {
            return new RatePlan(in);
        }

        @Override
        public RatePlan[] newArray(int size) {
            return new RatePlan[size];
        }
    };

    public Price getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
