package org.dbms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Driver {
    @Id
    String id;
    String name;
    Date licenseYear;
    Car car;

    public Driver() {}
    public Driver(String id, String name, Date licenseYear, Car car) {
        this.id = id;
        this.name = name;
        this.licenseYear = licenseYear;
        this.car = car;
    }
    public Driver(Long id, String name, Date licenseYear, Car car) {
        this.name = name;
        this.licenseYear = licenseYear;
        this.car = car;
    }

    public Driver(String name, Date licenseYear, Car car) {
        this.name = name;
        this.licenseYear = licenseYear;
        this.car = car;
    }

    public Long getId() {
        return 1l;
    }
    public String getMongoId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public Date getLicenseYear() {
        return licenseYear;
    }
}
