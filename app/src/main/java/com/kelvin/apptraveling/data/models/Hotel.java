package com.kelvin.apptraveling.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Hotel implements Parcelable {
    private String name;
    private Address address;
    private GuestReviews guestReviews;
    private RatePlan ratePlan;
    private OptimizedThumbUrls optimizedThumbUrls;
    private Coordinate coordinate;

    public Hotel(String name, Address address, GuestReviews guestReviews, RatePlan ratePlan, OptimizedThumbUrls optimizedThumbUrls, Coordinate coordinate) {
        this.name = name;
        this.address = address;
        this.guestReviews = guestReviews;
        this.ratePlan = ratePlan;
        this.optimizedThumbUrls = optimizedThumbUrls;
        this.coordinate = coordinate;
    }

    protected Hotel(Parcel in) {
        name = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        guestReviews = in.readParcelable(GuestReviews.class.getClassLoader());
        ratePlan = in.readParcelable(RatePlan.class.getClassLoader());
        optimizedThumbUrls = in.readParcelable(OptimizedThumbUrls.class.getClassLoader());
        coordinate = in.readParcelable(Coordinate.class.getClassLoader());
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GuestReviews getGuestReviews() {
        return guestReviews;
    }

    public void setGuestReviews(GuestReviews guestReviews) {
        this.guestReviews = guestReviews;
    }

    public RatePlan getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(RatePlan ratePlan) {
        this.ratePlan = ratePlan;
    }

    public OptimizedThumbUrls getOptimizedThumbUrls() {
        return optimizedThumbUrls;
    }

    public void setOptimizedThumbUrls(OptimizedThumbUrls optimizedThumbUrls) {
        this.optimizedThumbUrls = optimizedThumbUrls;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", guestReviews=" + guestReviews +
                ", ratePlan=" + ratePlan +
                ", optimizedThumbUrls=" + optimizedThumbUrls +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
    }
}

