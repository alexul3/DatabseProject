package org.dbms.dto;

import org.dbms.models.Client;
import org.dbms.models.Driver;
import org.dbms.models.Order;

public class CreateOrderDTO {
    private String from;
    private String to;
    private int deliveryWeight;
    private String driverId;
    private String login;
    private boolean isFragile;

    public CreateOrderDTO() {}
    public CreateOrderDTO(String from, String to, int deliveryWeight, String driverId, String login, boolean isFragile) {
        this.deliveryWeight = deliveryWeight;
        this.driverId = driverId;
        this.from = from;
        this.to = to;
        this.login = login;
        this.isFragile = isFragile;
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
    public String getLogin() {
        return login;
    }
    public String getDriverId() {
        return driverId;
    }
    public boolean isFragile() {
        return isFragile;
    }

    public boolean getIsFragile() {
        return isFragile;
    }
}
