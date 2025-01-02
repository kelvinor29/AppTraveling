package com.kelvin.apptraveling.data.models;

public class Hotel {
    private String name;
    private Address address;
    private GuestReviews guestReviews;
    private RatePlan ratePlan;
    private OptimizedThumbUrls optimizedThumbUrls;

    public Hotel(String name, Address address, GuestReviews guestReviews, RatePlan ratePlan, OptimizedThumbUrls optimizedThumbUrls) {
        this.name = name;
        this.address = address;
        this.guestReviews = guestReviews;
        this.ratePlan = ratePlan;
        this.optimizedThumbUrls = optimizedThumbUrls;
    }

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
}

