package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.OrderDetail;
import com.coffekyun.cinema.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends OrderDetailCustomRepository, JpaRepository<OrderDetail, OrderDetailId> {

    List<OrderDetail> findOrderDetailByOrderId(String id);

}
