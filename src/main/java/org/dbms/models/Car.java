package org.dbms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Objects;

public class Car {
    @Id
    private String id;
    private String model;
    private Date year;
    private double cost;
    private Parking parking;

    public Car() {}
    public Car(String id, String model, Date year, double cost, Parking parking) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.cost = cost;
        this.parking = parking;
    }

    public Car(Long id, String model, Date year, double cost, Parking parking) {
        this.model = model;
        this.year = year;
        this.cost = cost;
        this.parking = parking;
    }

    public Car(String model, Date year, double cost, Parking parking) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.cost = cost;
        this.parking = parking;
    }

    public Long getId() {
        return 1l;
    }
    public String getMongoId() {return id; }

    public Date getYear() {
        return year;
    }

    public double getCost() {
        return cost;
    }

    public Parking getParking() {
        return parking;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(car.cost, cost) == 0 && Objects.equals(id, car.id) && Objects.equals(model, car.model) && Objects.equals(year, car.year) && Objects.equals(parking, car.parking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, year, cost, parking);
    }
}
