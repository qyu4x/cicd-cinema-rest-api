package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.model.dto.SeatDetailRequest;
import com.coffekyun.cinema.model.dto.SeatDetailResponse;
import com.coffekyun.cinema.entity.OrderDetail;

import java.util.List;

public interface SeatDetailService {

    SeatDetailResponse addSeatAndStudio(SeatDetailRequest seatDetailRequest);

    List<SeatDetailResponse> getSeatIfAvailable(Boolean status);

    Boolean checkSeatAvailability(OrderRequest orderRequest);

    Boolean checkNumberOfSeat(List<OrderDetail> orderDetails, OrderRequest orderRequest);

}
