package org.dbms.repos;

import org.dbms.models.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepo extends MongoRepository<Car, String> {
}
