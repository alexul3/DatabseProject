package org.dbms.storageContracts;

import org.dbms.models.Car;
import org.dbms.models.Order;

import java.util.List;

public interface IOrderStorage {
    List<Order> readAll();
    List<Order> readPageByClientId(int size, int page, Long clientId);
    int getCountByClientId(Long clientId);
    Order getElementById(Long orderId);
    List<Order> getOrdersByClientId(Long clientId);
    void insert(Order order);
    void delete(Long orderId);
}
