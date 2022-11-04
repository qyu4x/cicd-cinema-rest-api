package com.coffekyun.cinema.model.dto;

import com.coffekyun.cinema.entity.Seat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SeatRequest {

    @NotEmpty(message = "The seat code is required.")
    private String seatCode;

    private List<SeatDetailRequest> seatDetails;
    public Seat toSeat() {
        return Seat.builder()
                .id(this.seatCode)
                .seatCode(this.seatCode)
                .build();
    }
}
