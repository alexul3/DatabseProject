package org.dbms.data;

import org.dbms.dto.CreateOrderDTO;
import org.dbms.models.Car;
import org.dbms.models.Client;
import org.dbms.models.Driver;
import org.dbms.models.Parking;
import org.dbms.service.OrderService;
import org.dbms.storageContracts.ICarStorage;
import org.dbms.storageContracts.IClientStorage;
import org.dbms.storageContracts.IDriverStorage;
import org.dbms.storageContracts.IParkingStorage;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DataPerform {
    private Connection connection;
    private IParkingStorage parkingStorage;
    private ICarStorage carStorage;


    private static String surnames[] = {"Иванов", "Петров", "Сидоров", "Александров", "Панов", "Ежов", "Зотов", "Котов", "зощенков"};
    private static String initials[] = {"А", "Б", "В", "Г", "Д", "Е", "И", "К", "Л", "М", "Н", "О", "П", "С"};

    private static String cities[] = {"Ульяновск", "Самара", "Москва", "Уфа", "Казань", "Нижний новгород", "Санкт петербург"};
    private static String streets[] = {"пр. Нариманова", "ул. Ленина", "ул. Карла Маркса", "ул. Радищева", "ул. Гагарина"};

    private final static String readRecords;
    private final static String readRecordById;
    private final static String insertRecord;
    private final static String updateRecord;
    private final static String deleteRecord;

    static
    {
        readRecords = "SELECT id, model, year, cost, parkingId FROM cars";
        readRecordById = new StringBuilder()
                .append("SELECT id, model, year, cost, parkingId FROM cars")
                .append("\n")
                .append("WHERE id = ?").toString();
        updateRecord = new StringBuilder("UPDATE cars\n")
                .append("UPDATE cars\n")
                .append("SET model = ?,\n")
                .append("year = ?,\n")
                .append("cost = ?,\n")
                .append("parkingid = ?\n")
                .append("WHERE id = ?").toString();

        insertRecord = "INSERT INTO cars (model, year, cost, parkingId) VALUES(?, ?, ?, ?)";
        deleteRecord = "DELETE FROM cars WHERE id = ?";
    }

    public DataPerform(Connection connection, IParkingStorage parkingStorage, ICarStorage carStorage) {
        this.connection = connection;
        this.parkingStorage = parkingStorage;
        this.carStorage = carStorage;
    }

    private String generateStr() {
        Random rnd = new Random();
        String res = "";

        int len = rnd.nextInt(5) + 3;
        for(int i = 0; i < len; ++i) {
            char ch = (char)(rnd.nextInt(25) + 'A');
            res += ch;
        }

        return res;
    }

    private String generateName() {
        Random rnd = new Random();
        return surnames[rnd.nextInt(9)] + " " + initials[rnd.nextInt(14)] + ". " + initials[rnd.nextInt(14)];
    }

    private Date generateDate() {
        Random rnd = new Random();
        int year = rnd.nextInt(21) + 100;
        int month = rnd.nextInt(12);
        int day = rnd.nextInt(26);

        Date date = new Date();
        date.setYear(year);
        date.setMonth(month);
        date.setDate(day);

        return date;
    }

    private String generateAddress() {
        Random rnd = new Random();
        return cities[rnd.nextInt(cities.length)] + " " + streets[rnd.nextInt(streets.length)] + " д. " + rnd.nextInt(25) + 1;
    }

    public long select() {
        long res = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecords);

            long start = System.currentTimeMillis();
            ResultSet resultSet = preparedStatement.executeQuery();
            long end = System.currentTimeMillis();
            res += end - start;
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public long insert(int count) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cars");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch(Exception ex) {

        }

        List<Parking> parkings = parkingStorage.readAll();
        Random rnd = new Random();
        String models[] = {"Газель", "Камаз", "Volvo", "Трактор"};

        long res = 0;

        for(int i = 0; i < count; ++i) {
            int modelIndex = rnd.nextInt(4);
            double cost = rnd.nextDouble(400000) + 50000;

            Car car = new Car(models[modelIndex], generateDate(), cost, parkings.get(rnd.nextInt(parkings.size())));

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertRecord);

                preparedStatement.setString(1, car.getModel());
                preparedStatement.setDate(2, new java.sql.Date(car.getYear().getTime()));
                preparedStatement.setDouble(3, car.getCost());
                preparedStatement.setLong(4, car.getParking().getId());

                long start = System.currentTimeMillis();
                preparedStatement.executeUpdate();
                long end = System.currentTimeMillis();
                res += end - start;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public long update(int count) {
        List<Car> cars = carStorage.readAll();
        List<Parking> parkings = parkingStorage.readAll();
        Random rnd = new Random();
        String models[] = {"Газель", "Камаз", "Volvo", "Трактор"};

        long res = 0;
        for(int i = 0; i < count; ++i) {
            int modelIndex = rnd.nextInt(4);
            double cost = rnd.nextDouble(400000) + 50000;

            Car car = new Car(models[modelIndex], generateDate(), cost, parkings.get(rnd.nextInt(parkings.size())));

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateRecord);

                preparedStatement.setString(1, car.getModel());
                preparedStatement.setDate(2, new java.sql.Date(car.getYear().getTime()));
                preparedStatement.setDouble(3, car.getCost());
                preparedStatement.setLong(4, car.getParking().getId());
                preparedStatement.setLong(5, cars.get(0).getId());

                long start = System.nanoTime();
                preparedStatement.executeUpdate();
                long end = System.nanoTime();
                res += end - start;

            } catch (Exception ex) {
            }

        }
        return res;
    }

    public long delete() {
        long res = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cars");
            long start = System.currentTimeMillis();
            preparedStatement.executeUpdate();
            long end = System.currentTimeMillis();
            res = end - start;
            preparedStatement.close();
        } catch(Exception ex) {

        }

        return res;
    }
}
