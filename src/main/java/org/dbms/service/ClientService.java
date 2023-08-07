package org.dbms.service;

import org.dbms.dto.ClientLoginDto;
import org.dbms.dto.ClientSignupDto;
import org.dbms.exceptions.UserExistsException;
import org.dbms.models.Client;
import org.dbms.repos.ClientRepo;
import org.dbms.storageContracts.IClientStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ClientService {
    IClientStorage clientStorage;
    ClientRepo clientRepo;
    public ClientService(IClientStorage clientStorage, ClientRepo clientRepo) {
        this.clientStorage = clientStorage;
        this.clientRepo = clientRepo;
    }
    @Transactional
    public String signup(ClientSignupDto client) {
        if(clientStorage.getByLogin(client.getLogin()) != null) {
            throw new UserExistsException(client.getLogin());
        }

        Client res = clientRepo.insert(new Client(client.getName(), client.getPhone(), client.getLogin(), client.getPassword()));
        return res.getId() + "";
    }
    @Transactional
    public boolean login(ClientLoginDto client) {
        Client c = clientRepo.findByLogin(client.getLogin()).get(0);
        if(c == null) return false;
        if(!Objects.equals(c.getPassword(), client.getPassword())) return false;

        return true;
    }

    @Transactional
    public Client getClientByLogin(String login) {
        return clientRepo.findByLogin(login).get(0);
    }
}
