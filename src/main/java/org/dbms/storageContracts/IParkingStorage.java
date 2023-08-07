package org.dbms.storageContracts;

import org.dbms.models.Car;
import org.dbms.models.Parking;

import java.util.List;

public interface IParkingStorage {
    List<Parking> readAll();
    Parking getElementById(Long parkingId);
    void insert(Parking parking);
    void delete(Long parkingId);
}
