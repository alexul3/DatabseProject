package org.dbms.data;

import org.dbms.dto.CreateOrderDTO;
import org.dbms.models.Car;
import org.dbms.models.Client;
import org.dbms.models.Driver;
import org.dbms.models.Parking;
import org.dbms.repos.CarRepo;
import org.dbms.repos.ClientRepo;
import org.dbms.repos.DriverRepo;
import org.dbms.repos.ParkingRepo;
import org.dbms.service.OrderService;
import org.dbms.storageContracts.ICarStorage;
import org.dbms.storageContracts.IClientStorage;
import org.dbms.storageContracts.IDriverStorage;
import org.dbms.storageContracts.IParkingStorage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataGenerator {
    private CarRepo carRepo;
    private ParkingRepo parkingRepo;
    private ClientRepo clientRepo;
    private OrderService orderService;
    private DriverRepo driverRepo;


    private static String surnames[] = {"Иванов", "Петров", "Сидоров", "Александров", "Панов", "Ежов", "Зотов", "Котов", "зощенков"};
    private static String initials[] = {"А", "Б", "В", "Г", "Д", "Е", "И", "К", "Л", "М", "Н", "О", "П", "С"};

    private static String cities[] = {"Ульяновск", "Самара", "Москва", "Уфа", "Казань", "Нижний новгород", "Санкт петербург"};
    private static String streets[] = {"пр. Нариманова", "ул. Ленина", "ул. Карла Маркса", "ул. Радищева", "ул. Гагарина"};

    public DataGenerator(CarRepo carRepo, ParkingRepo parkingRepo,
                         ClientRepo clientRepo, OrderService orderService, DriverRepo driverRepo) {
        this.carRepo = carRepo;
        this.parkingRepo = parkingRepo;
        this.clientRepo = clientRepo;
        this.orderService = orderService;
        this.driverRepo = driverRepo;
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

    private void generateCars() {
        List<Parking> parkings = parkingRepo.findAll();
        Random rnd = new Random();
        String models[] = {"Газель", "Камаз", "Volvo", "Трактор"};

        for(int i = 0; i < 1000; ++i) {
            int modelIndex = rnd.nextInt(4);
            double cost = rnd.nextDouble(400000) + 50000;

            Car car = new Car(models[modelIndex], generateDate(), cost, parkings.get(rnd.nextInt(parkings.size())));
            carRepo.insert(car);
        }
    }

    private void generateClients() {
        Random rnd = new Random();
        Set<String> logins = new TreeSet<String>();

        for(int i = 0; i < 500; ++i) {
            String login = generateStr();
            while(logins.contains(login)) login = generateStr();
            logins.add(login);

            String name = generateName();
            String pass = generateStr();
            String phone = "8927";

            for(int j = 0; j < 7; ++j) {
                phone += rnd.nextInt(9);
            }

            Client client = new Client(name, phone, login, pass);
            clientRepo.insert(client);
        }
    }

    private void generateParkings() {
        Random rnd = new Random();

        for(int i = 0; i < 600; ++i) {
            String address = generateAddress();
            Parking parking = new Parking(address, rnd.nextInt(20) + 10);
            parkingRepo.insert(parking);
        }
    }

    private void generateDrivers() {
        List<Car> cars = carRepo.findAll();
        Random rnd = new Random();

        for(int i = 0; i < 1000; ++i) {
            Car car = cars.get(rnd.nextInt(cars.size()));
            cars.remove(car);

            Driver driver = new Driver(generateName(), generateDate(), car);
            driverRepo.insert(driver);
        }
    }

    private void generateOrders() {
        List<Client> clients = clientRepo.findAll();
        List<Driver> drivers = driverRepo.findAll();
        Random rnd = new Random();

        for(int i = 0; i < 2000; ++i) {
            String from = generateAddress();
            String to = generateAddress();
            int deliveryWeight = rnd.nextInt(30) + 5;

            String clientLogin = clients.get(rnd.nextInt(clients.size())).getLogin();
            String driverId = drivers.get(rnd.nextInt(drivers.size())).getMongoId();

            CreateOrderDTO dto = new CreateOrderDTO(from, to, deliveryWeight, driverId, clientLogin, rnd.nextBoolean());
            orderService.createOrder(dto);
        }
    }

    public void generate() {
        generateClients();
        generateParkings();
        generateCars();
        generateDrivers();
        generateOrders();
    }
}
