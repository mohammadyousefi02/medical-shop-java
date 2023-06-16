package com.example.medicalshop.order;

import com.example.medicalshop.cart.CartItem;
import com.example.medicalshop.cart.CartService;
import com.example.medicalshop.exceptions.BadRequestException;
import com.example.medicalshop.exceptions.NotFoundException;
import com.example.medicalshop.product.Product;
import com.example.medicalshop.user.User;
import com.example.medicalshop.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Transactional
    public Order addOrder(OrderDTO orderDTO, Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) throw new NotFoundException("user does not exist");
        Order order = new Order();
        order.setAddress(orderDTO.getAddress());
        order.setName(orderDTO.getName());
        order.setCity(orderDTO.getCity());
        order.setProvince(orderDTO.getProvince());
        order.setZipcode(orderDTO.getZipcode());
        order.setNumber(orderDTO.getNumber());
        order.setUser(user.get());
        List<CartItem> cartItems = user.get().getCart();
        List<OrderItem> orderItems = new ArrayList<>();
        if(cartItems.isEmpty()) throw new BadRequestException("you should have at least an item in cart");
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            Product product = cartItem.getProduct();
            orderItem.setProduct(product);
            orderItem.setCompany(product.getCompany());
            orderItem.setPrice(product.getPrice());
            orderItem.setTitle(product.getTitle());
            orderItem.setMadeIn(product.getMadein());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order = orderRepository.save(order);

        cartService.clear(userId);
        return order;
    }
}
