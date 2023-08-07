package org.dbms.storageContracts;

import org.dbms.models.Car;

import java.util.List;

public interface ICarStorage {
    List<Car> readAll();
    Car getElementById(Long carId);
    void insert(Car car);
    void delete(Long carId);
}
