package org.dbms.repos;

import org.dbms.models.Client;
import org.dbms.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepo extends MongoRepository<Order, String> {
    Page<Order> findByClient(Client id, Pageable pg);
}
