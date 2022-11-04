package com.coffekyun.cinema.entity;

import org.springframework.context.annotation.Configuration;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderDetailId implements Serializable {

    @Column(name = "orders_id")
    String orderId;

    @Column(name = "schedules_id")
    String scheduleId;

}
