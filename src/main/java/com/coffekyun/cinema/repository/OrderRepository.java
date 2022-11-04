package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Invoice;
import com.coffekyun.cinema.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends OrderCustomRepository, JpaRepository<Order, String> {

    @Modifying
    @Query(
            value = "UPDATE orders SET total_price = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    void updateTotalPriceById(BigDecimal totalPrice, String id);

    @Query(
            value = "SELECT os.id FROM orders as os WHERE os.users_id = ?1",
            nativeQuery = true
    )
    List<String> findByUserId(String id);


}
