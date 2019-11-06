package com.lx.demo.springbootmapstruct.domain;

public class Car {

    public Car() {
    }

    public Car(String make, int numberOfSeats) {
        this.make = make;
        this.numberOfSeats = numberOfSeats;
    }

    private String make;
    private int numberOfSeats;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}

