package com.coffekyun.cinema.entity;

import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seats_detail")
public class SeatDetail {

    @EmbeddedId
    private SeatDetailId seatDetailId;

    @MapsId("idSeat")
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "seats_code",
            referencedColumnName = "id"
    )
    private Seat seat;

    @MapsId("idStudio")
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "studios_name",
            referencedColumnName = "id"
    )
    private Studio studio;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "schedules_id",
            referencedColumnName = "id"
    )
    private Schedule schedule;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "orders_id",
            referencedColumnName = "id"
    )
    private Order order;

    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
