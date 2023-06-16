package com.example.medicalshop.order;

import com.example.medicalshop.auth.Authorize;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Authorize
    public Order order(@RequestBody OrderDTO orderDTO, HttpServletRequest request) {
        return orderService.addOrder(orderDTO, (Long) request.getAttribute("userId"));
    }
}
