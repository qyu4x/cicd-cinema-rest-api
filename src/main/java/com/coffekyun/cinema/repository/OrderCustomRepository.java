package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderCustomRepository {
    List<Order> getOrderDetail(String idOrder);
}
