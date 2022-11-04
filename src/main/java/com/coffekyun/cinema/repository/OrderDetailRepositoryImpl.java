package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.OrderDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderDetailRepositoryImpl implements OrderDetailCustomRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public void addOrderDetail(OrderDetail orderDetail) {
        try {
            String sql = "INSERT INTO orders_detail(orders_id, schedules_id, price, quantity )VALUES(?, ?, ?, ?)";
            entityManager.createNativeQuery(sql)
                    .setParameter(1, orderDetail.getOrder())
                    .setParameter(2, orderDetail.getSchedule())
                    .setParameter(3, orderDetail.getPrice())
                    .setParameter(4, orderDetail.getQuantity())
                    .executeUpdate();

        }catch (Exception exception) {
            throw new RuntimeException("Oops failed insert data into database");
        }
    }


}
