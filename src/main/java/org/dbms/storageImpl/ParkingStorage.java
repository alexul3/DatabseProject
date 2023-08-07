package org.dbms.storageImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.dbms.models.Client;
import org.dbms.models.Parking;
import org.dbms.storageContracts.IParkingStorage;
import org.springframework.stereotype.Component;


@Component
public class ParkingStorage implements IParkingStorage {

    private final static String readRecords;
    private final static String readRecordById;
    private final static String insertRecord;
    private final static String deleteRecord;

    static
    {
        readRecords = "SELECT id, address, capacity FROM parkings";
        readRecordById = new StringBuilder()
                .append("SELECT id, address, capacity FROM parkings")
                .append("\n")
                .append("WHERE id = ?").toString();
        insertRecord = "INSERT INTO parkings (address, capacity) VALUES(?, ?)";
        deleteRecord = "DELETE FROM parkings WHERE id = ?";
    }


    private final Connection connection;

    public ParkingStorage(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Parking> readAll() {
        ArrayList<Parking> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecords);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String address = resultSet.getString(2);
                int capacity = resultSet.getInt(3);

                list.add(new Parking(id, address, capacity));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Parking getElementById(Long parkingId) {
        Parking res = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecordById);
            preparedStatement.setLong(1, parkingId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String address = resultSet.getString(2);
                int capacity = resultSet.getInt(3);

                res = new Parking(id, address, capacity);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void insert(Parking parking) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertRecord);

            preparedStatement.setString(1, parking.getAddress());
            preparedStatement.setInt(2, parking.getCapacity());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long parkingId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteRecord);
            preparedStatement.setLong(1, parkingId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}