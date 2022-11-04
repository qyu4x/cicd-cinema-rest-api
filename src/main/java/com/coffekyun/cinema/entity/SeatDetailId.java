package com.coffekyun.cinema.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SeatDetailId implements Serializable {

    @Column(name = "studios_id")
    String idStudio;

    @Column(name = "seats_id")
    String idSeat;

}
