package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.entity.Order;
import com.coffekyun.cinema.entity.OrderDetail;
import com.coffekyun.cinema.entity.SeatDetail;

import java.util.List;

public interface SeatDetailCustomRepository {

    void addStudioAndSeat(SeatDetail seatDetail);

    void updateSeatDetail(Order order, List<OrderDetail> scheduleList, OrderRequest orderRequest);

}
