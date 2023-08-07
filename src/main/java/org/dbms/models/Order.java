package org.dbms.models;

import org.springframework.data.annotation.Id;

public class Order {
    @Id
    private String id;
    private String from;
    private String to;
    private double price;
    private int deliveryWeight;
    private Driver driver;
    private Client client;

    public Order() {}

    public Order(String id, String from, String to, double price, int deliveryWeight, Driver driver, Client client) {
        this.id = id;
        this.from = from;
        this.price = price;
        this.to = to;
        this.deliveryWeight = deliveryWeight;
        this.driver = driver;
        this.client = client;
    }

    public Order(Long id, String from, String to, double price, int deliveryWeight, Driver driver, Client client) {
        this.from = from;
        this.price = price;
        this.to = to;
        this.deliveryWeight = deliveryWeight;
        this.driver = driver;
        this.client = client;
    }

    public Order(String from, String to, double price, int deliveryWeight, Driver driver, Client client) {
        this.from = from;
        this.to = to;
        this.deliveryWeight = deliveryWeight;
        this.price = price;
        this.driver = driver;
        this.client = client;
    }

    public Long getId() {
        return 1l;
    }
    public String getMongoId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public double getPrice() {
        return price;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getDeliveryWeight() {
        return deliveryWeight;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
