package com.kelvin.apptraveling.feature.home.domain;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.data.models.Car;

import java.util.ArrayList;

public class CarsProvider {

    private ArrayList<Car> carsList = new ArrayList<>();

    public static class CarFeatures {
        private @ColorRes int colorCar;
        private @DrawableRes int imgCar;

        public CarFeatures(int colorCar, int imgCar) {
            this.colorCar = colorCar;
            this.imgCar = imgCar;
        }

        @ColorRes
        public int getColorCar() {
            return colorCar;
        }

        @DrawableRes
        public int getImgCar() {
            return imgCar;
        }
    }

    public CarsProvider() {
        carsList.add(new Car("Classic Car", 34.0f, new CarFeatures(R.color.classicCar, R.drawable.img_vehicle_classic_car) ));
        carsList.add(new Car("Sport Car", 55.0f, new CarFeatures(R.color.sportCar, R.drawable.img_vehicle_sport_car)));
        carsList.add(new Car("Flying Car", 500.0f, new CarFeatures(R.color.flyingCar, R.drawable.img_vehicle_flying_car)));
        carsList.add(new Car("Electric Car", 45.0f, new CarFeatures(R.color.electricCar, R.drawable.img_vehicle_electric_car)));
        carsList.add(new Car("Motorhome", 23.0f, new CarFeatures(R.color.motorhomeCar, R.drawable.img_vehicle__motor_home)));
        carsList.add(new Car("PickUp", 10.0f, new CarFeatures(R.color.pickUpCar, R.drawable.img_vehicle_pick_up_car)));
        carsList.add(new Car("AirPlane", 11.0f, new CarFeatures(R.color.airPlane, R.drawable.img_vehicle_air_plain)));
        carsList.add(new Car("Bus", 14.0f, new CarFeatures(R.color.busCar, R.drawable.img_vehicle_bus)));
    }


    public ArrayList<Car> getCarsList() {
        return carsList;
    }

    public void setCarsList(ArrayList<Car> carsList) {
        this.carsList = carsList;
    }
}
