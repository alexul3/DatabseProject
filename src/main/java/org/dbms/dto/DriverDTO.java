package org.dbms.dto;

import org.dbms.models.Car;
import org.dbms.models.Driver;

import java.util.Date;

public class DriverDTO {
    String id;
    String name;
    Date licenseYear;
    CarDTO car;

    public DriverDTO(Driver driver) {
        if(driver == null) return;
        this.id = driver.getMongoId();
        this.name = driver.getName();
        this.licenseYear = driver.getLicenseYear();
        this.car = new CarDTO(driver.getCar());
    }

    public Date getLicenseYear() {
        return licenseYear;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return 1l;
    }

    public String getMongoId() {return id;}

    public void setMongoId(String id) {
        this.id = id;
    }

    public void setId(Long id) {
    }

    public void setLicenseYear(Date licenseYear) {
        this.licenseYear = licenseYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }
}
