package org.dbms.storageContracts;

import org.dbms.models.Car;
import org.dbms.models.Driver;
import org.dbms.models.Order;
import org.dbms.searchModels.DriverSearch;

import java.util.List;

public interface IDriverStorage {
    List<Driver> readAll();
    List<Driver> readPage(int size, int page);
    int getFilteredCount(DriverSearch filter);
    Driver getElementById(Long driverId);
    void insert(Driver driver);
    void delete(Long driverId);
    List<Driver> getFilteredList(DriverSearch filter);
    List<Driver> getFilteredPage(DriverSearch filter, int size, int page);
}
