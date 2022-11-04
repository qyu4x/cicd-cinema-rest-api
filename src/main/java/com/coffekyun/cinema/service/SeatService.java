package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.SeatRequest;
import com.coffekyun.cinema.model.dto.SeatResponse;

public interface SeatService {

    SeatResponse add(SeatRequest seatRequest);

}
