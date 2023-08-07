package org.dbms.service;

import org.dbms.dto.DriverDTO;
import org.dbms.models.Driver;
import org.dbms.repos.DriverRepo;
import org.dbms.searchModels.DriverSearch;
import org.dbms.storageContracts.IDriverStorage;
import org.dbms.storageImpl.DriverStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DriverService {
    private DriverRepo driverRepo;

    public DriverService(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Transactional
    public int getFilteredCount(int expFrom, int expTo, int size) {
        if(expFrom == -1) expFrom = 0;
        if(expTo == -1) expTo = 100;

        Date from = new Date();
        Date to = new Date();

        from.setYear(from.getYear() - expTo);
        to.setYear(to.getYear() - expFrom);

        Page<Driver> res = driverRepo.findBylicenseYearBetween(from, to, PageRequest.of(1, size));
        return res.getTotalPages();
    }
    @Transactional
    public List<Driver> getFilteredPage(int expFrom, int expTo, int size, int page) {
        if(expFrom == -1) expFrom = 0;
        if(expTo == -1) expTo = 100;

        Date from = new Date();
        Date to = new Date();

        from.setYear(from.getYear() - expTo);
        to.setYear(to.getYear() - expFrom);

        Page<Driver> res = driverRepo.findBylicenseYearBetween(from, to, PageRequest.of(page - 1, size));
        return res.getContent();
    }

    @Transactional
    public Driver getDriverById(String driverId) {
        return driverRepo.findById(driverId).get();
    }
}
