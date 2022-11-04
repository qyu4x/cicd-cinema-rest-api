package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.entity.Order;

public interface OrderService {

    void createUserOrder(OrderRequest orderRequest);
    Order createOrder(String idUser);

    Order findById(String id);

}
