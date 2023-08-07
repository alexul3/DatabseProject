package org.dbms.models;

import org.springframework.data.annotation.Id;

public class Client {
    @Id
    private String id;
    private String name;
    private String phone;
    private String login;
    private String password;

    public Client() {}
    public Client(String id, String name, String phone, String login, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.login = login;
        this.password = password;
    }

    public Client(Long id, String name, String phone, String login, String password) {
        this.name = name;
        this.phone = phone;
        this.login = login;
        this.password = password;
    }

    public Client(String name, String phone, String login, String password) {
        this.name = name;
        this.phone = phone;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return 1l;
    }

    public String getMongoId() {return id;}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
