package org.dbms.storageContracts;

import org.dbms.models.Car;
import org.dbms.models.Client;

import java.util.List;

public interface IClientStorage {
    List<Client> readAll();
    Client getElementById(Long clientId);
    void insert(Client client);
    void delete(Long clientId);
    Client getByLogin(String login);
    String getIdByLogin(String login);
}
