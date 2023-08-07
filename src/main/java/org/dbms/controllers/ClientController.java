package org.dbms.controllers;

import org.dbms.dto.ClientLoginDto;
import org.dbms.dto.ClientSignupDto;
import org.dbms.dto.DriverDTO;
import org.dbms.exceptions.UserExistsException;
import org.dbms.models.Client;
import org.dbms.repos.ClientRepo;
import org.dbms.searchModels.DriverSearch;
import org.dbms.service.ClientService;
import org.dbms.storageImpl.ClientStorage;
import org.dbms.storageImpl.DriverStorage;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {
    private final ClientService clientService;
    private final ClientRepo repo;

    public ClientController(ClientService clientService, ClientRepo repo) {
        this.clientService = clientService;
        this.repo = repo;
    }

    @PostMapping("/client")
    public String createUser(@RequestBody @Valid ClientSignupDto client, HttpServletResponse response) {
        String res = "";

        try {
            res = clientService.signup(client);
        } catch(UserExistsException err) {
            response.setStatus(403);
            return "Пользователь уже существует";
        }

        return res;
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid ClientLoginDto client, HttpServletResponse response) {
        if(clientService.login(client)) {
            return "success";
        } else {
            response.setStatus(403);
            return "Неверный логин или пароль";
        }
    }

    @GetMapping("/all-clients")
    public List<Client> getAll() {
        return repo.findAll();
    }
}
;