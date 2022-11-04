package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.model.dto.OrderResponse;
import com.coffekyun.cinema.entity.Order;

import javax.persistence.*;
import java.util.List;

public class OrderRepositoryImpl implements OrderCustomRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> getOrderDetail(String idOrder) {
        try {
            String sql = "SELECT us.name, us.email, os.id, os.total_price, os.created_at, ol.quantity, ss.price,ss.start_time, ss.end_time, sl.studios_name, sl.seats_code,fm.title FROM orders as os JOIN users as us ON os.users_id = us.id JOIN orders_detail as ol ON os.id = ol.orders_id JOIN schedules as ss ON ol.schedules_id = ss.id JOIN films as fm ON ss.films_id = fm.id JOIN seats_detail sl on os.id = sl.orders_id WHERE os.id = ?1";
            Query q = entityManager.createNativeQuery(sql);
            q.setParameter(1, idOrder);
            List<OrderResponse>  resultList =  q.getResultList();

            // bug

            return null;
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
