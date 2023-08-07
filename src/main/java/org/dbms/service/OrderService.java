package org.dbms.service;

import org.dbms.dto.CreateOrderDTO;
import org.dbms.models.Client;
import org.dbms.models.Driver;
import org.dbms.models.Order;
import org.dbms.repos.OrderRepo;
import org.dbms.storageContracts.IOrderStorage;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private ClientService clientService;
    private DriverService driverService;
    private OrderRepo orderRepo;

    public OrderService(ClientService clientService, DriverService driverService, OrderRepo orderRepo) {
        this.clientService = clientService;
        this.driverService = driverService;
        this.orderRepo = orderRepo;
    }

    @Transactional
    public List<Order> getOrdersPageByClient(String login, int size, int page) {
//        Client client = clientService.getClientByLogin(login);
//        return orderStorage.readPageByClientId(size, page, client.getId());
        Client client = clientService.getClientByLogin(login);
        return orderRepo.findByClient(client, PageRequest.of(page - 1, size)).getContent();
    }

    @Transactional
    public void createOrder(CreateOrderDTO orderDTO) {
        Client client = clientService.getClientByLogin(orderDTO.getLogin());
        Driver driver = driverService.getDriverById(orderDTO.getDriverId());
        Order order = new Order(orderDTO.getFrom(), orderDTO.getTo(), calculatePrice(orderDTO),
                                orderDTO.getDeliveryWeight(), driver, client);

        //orderStorage.insert(order);
        orderRepo.insert(order);
    }
    @Transactional
    public int getPagesCount(String login, int size) {
        Client client = clientService.getClientByLogin(login);
        return orderRepo.findByClient(client, PageRequest.of(1, size)).getTotalPages();
    }
    @Transactional
    private double calculatePrice(CreateOrderDTO orderDTO) {
        Driver driver = driverService.getDriverById(orderDTO.getDriverId());
        double frigileCoef = 1.0;
        if(orderDTO.isFragile()) frigileCoef = 1.1;

        double driverCoef = LocalDate.now().getYear() - driver.getLicenseYear().getYear() - 1900;
        driverCoef += 5;
        driverCoef /= 5;

        double res = orderDTO.getDeliveryWeight() * 50 * frigileCoef * driverCoef;
        return res;
    }
}
