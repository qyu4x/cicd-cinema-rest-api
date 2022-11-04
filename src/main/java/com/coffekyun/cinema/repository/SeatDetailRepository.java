package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Seat;
import com.coffekyun.cinema.entity.SeatDetail;
import com.coffekyun.cinema.entity.SeatDetailId;
import com.coffekyun.cinema.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatDetailRepository extends SeatDetailCustomRepository, JpaRepository<SeatDetail, SeatDetailId> {
    SeatDetail findByStudioAndSeat(Studio studio, Seat seat);

    List<SeatDetail> findByStatus(Boolean status);

    @Query(
            value = "SELECT * FROM seats_detail WHERE seats_code = ?1 AND studios_name = ?2",
            nativeQuery = true
    )
    List<SeatDetail> findBySeatCodeAndStudioName(String seatCode, String studioName);

    @Query(
            value = "SELECT * FROM seats_detail WHERE orders_id = ?1",
            nativeQuery = true
    )
    List<SeatDetail> findSeatDetailByOrdersId(String id);
}
