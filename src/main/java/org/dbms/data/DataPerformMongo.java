package org.dbms.data;

import org.dbms.dto.CreateOrderDTO;
import org.dbms.models.Car;
import org.dbms.models.Client;
import org.dbms.models.Driver;
import org.dbms.models.Parking;
import org.dbms.repos.CarRepo;
import org.dbms.repos.ParkingRepo;
import org.dbms.service.OrderService;
import org.dbms.storageContracts.ICarStorage;
import org.dbms.storageContracts.IClientStorage;
import org.dbms.storageContracts.IDriverStorage;
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
public class DataPerformMongo {
    private ParkingRepo parkingRepo;
    private CarRepo carRepo;


    private static String surnames[] = {"Иванов", "Петров", "Сидоров", "Александров", "Панов", "Ежов", "Зотов", "Котов", "зощенков"};
    private static String initials[] = {"А", "Б", "В", "Г", "Д", "Е", "И", "К", "Л", "М", "Н", "О", "П", "С"};

    private static String cities[] = {"Ульяновск", "Самара", "Москва", "Уфа", "Казань", "Нижний новгород", "Санкт петербург"};
    private static String streets[] = {"пр. Нариманова", "ул. Ленина", "ул. Карла Маркса", "ул. Радищева", "ул. Гагарина"};

    public DataPerformMongo(ParkingRepo parkingRepo, CarRepo carRepo) {
        this.parkingRepo = parkingRepo;
        this.carRepo = carRepo;
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
        long start = System.currentTimeMillis();
        carRepo.findAll();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public long insert(int count) {
        carRepo.deleteAll();
        List<Parking> parkings = parkingRepo.findAll();
        Random rnd = new Random();
        String models[] = {"Газель", "Камаз", "Volvo", "Трактор"};

        long res = 0;

        for(int i = 0; i < count; ++i) {
            int modelIndex = rnd.nextInt(4);
            double cost = rnd.nextDouble(400000) + 50000;

            Car car = new Car(models[modelIndex], generateDate(), cost, parkings.get(rnd.nextInt(parkings.size())));

            long start = System.currentTimeMillis();
            carRepo.insert(car);
            long end = System.currentTimeMillis();
            res += end - start;
        }

        return res;
    }

    public long update(int count) {
        List<Car> cars = carRepo.findAll();
        List<Parking> parkings = parkingRepo.findAll();
        Random rnd = new Random();
        String models[] = {"Газель", "Камаз", "Volvo", "Трактор"};

        long res = 0;
        for(int i = 0; i < count; ++i) {
            int modelIndex = rnd.nextInt(4);
            double cost = rnd.nextDouble(400000) + 50000;

            Car car = new Car(models[modelIndex], generateDate(), cost, parkings.get(rnd.nextInt(parkings.size())));

            long start = System.currentTimeMillis();
            carRepo.insert(car);
            long end = System.currentTimeMillis();
            res += end - start;
        }
        return res;
    }

    public long delete() {
        long start = System.currentTimeMillis();
        carRepo.deleteAll();
        long end = System.currentTimeMillis();
        return end - start;
    }
}

