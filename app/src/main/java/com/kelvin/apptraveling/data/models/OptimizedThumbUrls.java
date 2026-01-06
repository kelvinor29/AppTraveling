package com.kelvin.apptraveling.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class OptimizedThumbUrls implements Parcelable {
    private String srpDesktop;

    protected OptimizedThumbUrls(Parcel in) {
        srpDesktop = in.readString();
    }

    public static final Creator<OptimizedThumbUrls> CREATOR = new Creator<OptimizedThumbUrls>() {
        @Override
        public OptimizedThumbUrls createFromParcel(Parcel in) {
            return new OptimizedThumbUrls(in);
        }

        @Override
        public OptimizedThumbUrls[] newArray(int size) {
            return new OptimizedThumbUrls[size];
        }
    };

    public String getSrpDesktop() {
        return srpDesktop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(srpDesktop);
    }
}
