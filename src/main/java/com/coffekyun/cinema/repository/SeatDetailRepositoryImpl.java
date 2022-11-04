package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.entity.Order;
import com.coffekyun.cinema.entity.OrderDetail;
import com.coffekyun.cinema.entity.SeatDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SeatDetailRepositoryImpl implements SeatDetailCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public void addStudioAndSeat(SeatDetail seatDetail) {
        try {
            String sql = "INSERT INTO seats_detail(studios_name, seats_code) VALUES (?, ?)";
            entityManager.createNativeQuery(sql)
                    .setParameter(1, seatDetail.getStudio().getName())
                    .setParameter(2, seatDetail.getSeat().getSeatCode())
                    .executeUpdate();
        }catch (Exception exception) {
            throw new RuntimeException("Oops failed insert data into databse");
        }
    }

    @Override
    public void updateSeatDetail(Order order, List<OrderDetail> scheduleList, OrderRequest orderRequest) {
        try {
            for (int i = 0; i < scheduleList.size(); i++) {
                String scheduleId = scheduleList.get(i).getSchedule().getId();
                orderRequest.getOrderDetailRequests().get(i).getOrderSeatRequests()
                        .forEach(orderSeatRequest -> {
                            String sql = "UPDATE seats_detail SET status = ?,orders_id = ?, schedules_id = ?, updated_at = ? WHERE studios_name = ? AND seats_code = ?";
                            entityManager.createNativeQuery(sql)
                                    .setParameter(1, false)
                                    .setParameter(2, order.getId())
                                    .setParameter(3, scheduleId)
                                    .setParameter(4, LocalDateTime.now())
                                    .setParameter(5, orderSeatRequest.getStudioName())
                                    .setParameter(6, orderSeatRequest.getSeatCode())
                                    .executeUpdate();
                        });
            }
        }catch (Exception exception) {
            throw new RuntimeException("Oops failed insert data into databse " + exception.getMessage());
        }
    }
}
