package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Order;
import com.coffekyun.cinema.entity.OrderDetail;

import java.util.List;

public interface OrderDetailCustomRepository {
    void addOrderDetail(OrderDetail orderDetail);

}
