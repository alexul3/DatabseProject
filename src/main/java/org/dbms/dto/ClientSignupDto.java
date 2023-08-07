package org.dbms.dto;

public class ClientSignupDto {
    private String name;
    private String phone;
    private String login;
    private String password;

    public ClientSignupDto(String name, String phone, String login, String password) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
