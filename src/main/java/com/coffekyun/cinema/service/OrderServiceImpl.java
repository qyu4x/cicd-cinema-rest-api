package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.entity.Order;
import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createUserOrder(OrderRequest orderRequest) {

    }

    @Override
    public Order createOrder(String idUser) {
        log.info("make an order for user id {} ", idUser);
        try {
            User user = User.builder()
                    .id(idUser)
                    .build();
            Order order = Order.builder()
                    .id(UUID.randomUUID().toString())
                    .user(user)
                    .build();
            log.info("successfully placed an order for user id {} ", idUser);
            return orderRepository.save(order);
        }catch (Exception exception) {
            log.error("failed to make order for user id {} ", idUser);
            throw new RuntimeException("Oops failed to make an order");
        }
    }

    @Override
    public Order findById(String id) {
        log.info("do take orders based on user id {} ", id);
        Optional<Order> orderResponse = orderRepository.findById(id);
        if (orderResponse == null) {
            log.warn("user with id {} is not found", id);
            throw new DataNotFoundException("user with id " + id + " not found");
        } else {
            Order order = orderResponse.get();
            log.info("successfully found order with user id {} ", id);
            return Order.builder()
                    .id(order.getId())
                    .totalPrice(order.getTotalPrice())
                    .build();

        }
    }
}
