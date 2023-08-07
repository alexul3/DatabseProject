package org.dbms.storageImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.dbms.models.Car;
import org.dbms.models.Parking;
import org.dbms.storageContracts.ICarStorage;
import org.dbms.storageContracts.IParkingStorage;
import org.springframework.stereotype.Component;


@Component
public class CarStorage implements ICarStorage {

    private final static String readRecords;
    private final static String readRecordById;
    private final static String insertRecord;
    private final static String deleteRecord;

    static
    {
        readRecords = "SELECT id, model, year, cost, parkingId FROM cars";
        readRecordById = new StringBuilder()
                .append("SELECT id, model, year, cost, parkingId FROM cars")
                .append("\n")
                .append("WHERE id = ?").toString();

        insertRecord = "INSERT INTO cars (model, year, cost, parkingId) VALUES(?, ?, ?, ?)";
        deleteRecord = "DELETE FROM cars WHERE id = ?";
    }

    private final Connection connection;
    private final IParkingStorage parkingStorage;

    public CarStorage(Connection connection, IParkingStorage parkingStorage) {
        this.connection = connection;
        this.parkingStorage = parkingStorage;
    }
    @Override
    public List<Car> readAll() {
        ArrayList<Car> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecords);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String model = resultSet.getString(2);
                Date year = resultSet.getDate(3);
                Double cost = resultSet.getDouble(4);
                Long parkingId = resultSet.getLong(5);

                Parking p = parkingStorage.getElementById(parkingId);

                list.add(new Car(id, model, year, cost, p));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Car getElementById(Long carId) {
        Car res = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecordById);
            preparedStatement.setLong(1, carId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            Long id = resultSet.getLong(1);
            String model = resultSet.getString(2);
            Date year = resultSet.getDate(3);
            Double cost = resultSet.getDouble(4);
            Long parkingId = resultSet.getLong(5);

            Parking p = parkingStorage.getElementById(parkingId);

            res = new Car(id, model, year, cost, p);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void insert(Car car) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertRecord);

            preparedStatement.setString(1, car.getModel());
            preparedStatement.setDate(2, new java.sql.Date(car.getYear().getTime()));
            preparedStatement.setDouble(3, car.getCost());
            preparedStatement.setLong(4, car.getParking().getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long carId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteRecord);
            preparedStatement.setLong(1, carId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}