package org.dbms.dto;

import org.dbms.models.Client;
import org.dbms.models.Driver;
import org.dbms.models.Order;

public class OrderDTO {
    private Long id;
    private String from;
    private String to;
    private double price;
    private int deliveryWeight;
    private DriverDTO driver;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.from = order.getFrom();
        this.to = order.getTo();
        this.price = order.getPrice();
        this.deliveryWeight = order.getDeliveryWeight();
        this.driver = new DriverDTO(order.getDriver());
    }

    public Long getId() {
        return id;
    }
    public String getTo() {
        return to;
    }
    public String getFrom() {
        return from;
    }
    public int getDeliveryWeight() {
        return deliveryWeight;
    }
    public double getPrice() {
        return price;
    }
    public DriverDTO getDriver() {
        return driver;
    }
}
