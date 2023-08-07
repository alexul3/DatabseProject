package org.dbms.dto;

public class ClientLoginDto {
    private String login;
    private String password;

    public ClientLoginDto(String login, String password) {
        this.password = password;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
