package org.dbms.storageImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.dbms.models.*;
import org.dbms.storageContracts.IClientStorage;
import org.dbms.storageContracts.IDriverStorage;
import org.dbms.storageContracts.IOrderStorage;
import org.springframework.stereotype.Component;


@Component
public class OrderStorage implements IOrderStorage {

    private final static String readRecords;
    private final static String readPage;
    private final static String readCount;
    private final static String readRecordById;
    private final static String insertRecord;
    private final static String deleteRecord;
    private final static String readRecordByClientId;

    static
    {
        readRecords = "SELECT id, _from, _to, price, deliveryWeight, driverId, clientId FROM orders";
        readCount = "SELECT COUNT(*) FROM orders WHERE clientId = ?";
        readPage = new StringBuilder()
                .append("SELECT id, _from, _to, price, deliveryWeight, driverId, clientId FROM orders")
                .append("\n")
                .append("WHERE clientId = ?")
                .append("\n")
                .append("ORDER BY id")
                .append("\n")
                .append("OFFSET ?")
                .append("\n")
                .append("LIMIT ?").toString();

        readRecordById = new StringBuilder()
                .append("SELECT id, _from, _to, price, deliveryWeight, driverId, clientId FROM orders")
                .append("\n")
                .append("WHERE id = ?").toString();
        readRecordByClientId = new StringBuilder()
                .append("SELECT id, _from, _to, price, deliveryWeight, driverId, clientId FROM orders")
                .append("\n")
                .append("WHERE clientId = ?").toString();
        insertRecord = "INSERT INTO orders (_from, _to, price, deliveryWeight, driverId, clientId) VALUES(?, ?, ?, ?, ?, ?)";
        deleteRecord = "DELETE FROM orders WHERE id = ?";
    }

    private final Connection connection;
    private final IDriverStorage driverStorage;
    private final IClientStorage clientStorage;

    public OrderStorage(Connection connection, IDriverStorage driverStorage, IClientStorage clientStorage) {
        this.connection = connection;
        this.driverStorage = driverStorage;
        this.clientStorage = clientStorage;
    }

    @Override
    public List<Order> readAll() {
        ArrayList<Order> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecords);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String from = resultSet.getString(2);
                String to = resultSet.getString(3);
                Double price = resultSet.getDouble(4);
                int deliveryWeight = resultSet.getInt(5);
                Long driverId = resultSet.getLong(5);
                Long clientId = resultSet.getLong(5);

                Driver d = driverStorage.getElementById(driverId);
                Client c = clientStorage.getElementById(clientId);

                list.add(new Order(id, from, to, price, deliveryWeight, d, c));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Order> readPageByClientId(int size, int page, Long cid) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readPage);

            preparedStatement.setLong(1, cid);
            preparedStatement.setInt(2, page - 1);
            preparedStatement.setInt(3, size);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String from = resultSet.getString(2);
                String to = resultSet.getString(3);
                Double price = resultSet.getDouble(4);
                int deliveryWeight = resultSet.getInt(5);
                Long driverId = resultSet.getLong(6);
                Long clientId = resultSet.getLong(7);

                Driver d = driverStorage.getElementById(driverId);
                Client c = clientStorage.getElementById(clientId);

                list.add(new Order(id, from, to, price, deliveryWeight, d, c));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int getCountByClientId(Long clientId) {
        int res = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readCount);

            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                res = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Order getElementById(Long orderId) {
        Order res = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecordById);
            preparedStatement.setLong(1, orderId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Long id = resultSet.getLong(1);
            String from = resultSet.getString(2);
            String to = resultSet.getString(3);
            Double price = resultSet.getDouble(4);
            int deliveryWeight = resultSet.getInt(5);
            Long driverId = resultSet.getLong(5);
            Long clientId = resultSet.getLong(5);

            Driver d = driverStorage.getElementById(driverId);
            Client c = clientStorage.getElementById(clientId);

            res = new Order(id, from, to, price, deliveryWeight, d, c);

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Order> getOrdersByClientId(Long cid) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecordByClientId);
            preparedStatement.setLong(1, cid);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String from = resultSet.getString(2);
                String to = resultSet.getString(3);
                Double price = resultSet.getDouble(4);
                int deliveryWeight = resultSet.getInt(5);
                Long driverId = resultSet.getLong(6);
                Long clientId = resultSet.getLong(7);

                Driver d = driverStorage.getElementById(driverId);
                Client c = clientStorage.getElementById(clientId);

                list.add(new Order(id, from, to, price, deliveryWeight, d, c));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertRecord);

            preparedStatement.setString(1, order.getFrom());
            preparedStatement.setString(2, order.getTo());
            preparedStatement.setDouble(3, order.getPrice());
            preparedStatement.setInt(4, order.getDeliveryWeight());
            preparedStatement.setLong(5, order.getDriver().getId());
            preparedStatement.setLong(6, order.getClient().getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long orderId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteRecord);
            preparedStatement.setLong(1, orderId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}