package org.dbms.repos;

import org.dbms.models.Parking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingRepo extends MongoRepository<Parking, String> {
}
