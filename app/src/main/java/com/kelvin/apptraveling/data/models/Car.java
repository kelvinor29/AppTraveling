package com.kelvin.apptraveling.data.models;

import com.kelvin.apptraveling.feature.home.domain.CarsProvider;

import java.util.Locale;

public class Car {
    private String carName;
    private double carCost;
    private CarsProvider.CarFeatures carFeatures;

    public Car(String carName, double carCost, CarsProvider.CarFeatures carFeatures) {
        this.carName = carName;
        this.carCost = carCost;
        this.carFeatures = carFeatures;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarCost() {
        return String.format(Locale.getDefault(), "$%.2f/day", carCost);
    }

    public void setCarCost(double carCost) {
        this.carCost = carCost;
    }

    public CarsProvider.CarFeatures getCarFeatures() {
        return carFeatures;
    }

    public void setCarFeatures(CarsProvider.CarFeatures carFeatures) {
        this.carFeatures = carFeatures;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carName='" + carName + '\'' +
                ", carCost=" + carCost +
                ", carFeatures=" + carFeatures +
                '}';
    }
}
