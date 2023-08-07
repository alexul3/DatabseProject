package org.dbms.repos;

import org.dbms.models.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface DriverRepo extends MongoRepository<Driver, String> {
    Page<Driver> findBylicenseYearBetween(Date from, Date to, Pageable pg);
    List<Driver> findBylicenseYearBetween(Date from, Date to);
}
