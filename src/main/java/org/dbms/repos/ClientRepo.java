package org.dbms.repos;

import org.dbms.models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClientRepo extends MongoRepository<Client, String> {
    List<Client> findByLogin(String login);
}
