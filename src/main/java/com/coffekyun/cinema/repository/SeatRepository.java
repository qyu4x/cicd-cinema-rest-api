package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, String> {

}
