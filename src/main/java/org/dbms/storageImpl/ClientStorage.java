package org.dbms.storageImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.dbms.models.Client;
import org.dbms.models.Parking;
import org.dbms.storageContracts.IClientStorage;
import org.springframework.stereotype.Component;


@Component
public class ClientStorage implements IClientStorage {

    private final static String readRecords;
    private final static String readRecordById;
    private final static String readRecordByLogin;
    private final static String insertRecord;
    private final static String deleteRecord;
    private final static String loginExistQuery;

    static
    {
        readRecords = "SELECT id, name, phone, login, password FROM clients";
        readRecordById = new StringBuilder()
                .append("SELECT id, name, phone, login, password FROM clients")
                .append("\n")
                .append("WHERE id = ?").toString();
        readRecordByLogin = new StringBuilder()
                .append("SELECT id, name, phone, login, password FROM clients")
                .append("\n")
                .append("WHERE login = ?").toString();
        insertRecord = "INSERT INTO clients (name, phone, login, password) VALUES(?, ?, ?, ?)";
        deleteRecord = "DELETE FROM clients WHERE id = ?";
        loginExistQuery = "SELECT id FROM clients WHERE login = ?";
    }

    private final Connection connection;

    public ClientStorage(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Client> readAll() {
        ArrayList<Client> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecords);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String phone = resultSet.getString(3);
                String login = resultSet.getString(4);
                String password = resultSet.getString(5);

                list.add(new Client(id, name, phone, login, password));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Client getElementById(Long clientId) {
        Client res = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecordById);
            preparedStatement.setLong(1, clientId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            String phone = resultSet.getString(3);
            String login = resultSet.getString(4);
            String password = resultSet.getString(5);

            res = new Client(id, name, phone, login, password);

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void insert(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertRecord);

            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhone());
            preparedStatement.setString(3, client.getLogin());
            preparedStatement.setString(4, client.getPassword());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long clientId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteRecord);
            preparedStatement.setLong(1, clientId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getByLogin(String clientLogin) {
        Client res = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecordByLogin);
            preparedStatement.setString(1, clientLogin);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            String phone = resultSet.getString(3);
            String login = resultSet.getString(4);
            String password = resultSet.getString(5);

            res = new Client(id, name, phone, login, password);

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getIdByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(loginExistQuery);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1) + "";
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}