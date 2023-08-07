package org.dbms.controllers;

import org.dbms.dto.ClientLoginDto;
import org.dbms.dto.ClientSignupDto;
import org.dbms.dto.CreateOrderDTO;
import org.dbms.dto.OrderDTO;
import org.dbms.exceptions.UserExistsException;
import org.dbms.models.Order;
import org.dbms.repos.OrderRepo;
import org.dbms.service.ClientService;
import org.dbms.service.OrderService;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final OrderRepo repo;

    public OrderController(OrderService orderService, OrderRepo repo) {
        this.orderService = orderService;
        this.repo = repo;
    }

    @GetMapping("/orders")
    public List<OrderDTO> getOrders(@RequestParam String login, @RequestParam int size, @RequestParam int page) {
        return orderService.getOrdersPageByClient(login, size, page).stream().map(OrderDTO::new).toList();
    }

    @PostMapping("/orders")
    public String makeOrder(@RequestBody @Valid CreateOrderDTO order) {
        orderService.createOrder(order);
        return "success";
    }

    @GetMapping("/orders/pages-count")
    public int getPagesCount(@RequestParam String login, @RequestParam int size) {
        return orderService.getPagesCount(login, size);
    }

    @GetMapping("/all-orders")
    public List<Order> getAll() {
        return repo.findAll();
    }
}
;