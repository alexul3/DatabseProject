package org.dbms.dto;

import org.dbms.models.Car;

import java.util.Date;

public class CarDTO {
    private String id;
    private String model;
    private Date year;
    private double cost;

    public CarDTO(Car car) {
        if(car == null) return;

        this.id = car.getMongoId();
        this.model = car.getModel();
        this.year = car.getYear();
        this.cost = car.getCost();
    }

    public Long getId() {
        return 1l;
    }

    public String getMongoId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getCost() {
        return cost;
    }

    public Date getYear() {
        return year;
    }

    public void setId(Long id) {
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Date year) {
        this.year = year;
    }
}
