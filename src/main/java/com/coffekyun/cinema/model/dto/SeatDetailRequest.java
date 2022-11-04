package com.coffekyun.cinema.model.dto;

import com.coffekyun.cinema.entity.Seat;
import com.coffekyun.cinema.entity.Studio;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SeatDetailRequest {

    @NotEmpty(message = "The studio name is required.")
    private String studioName;

    @NotEmpty(message = "The seat code is required.")
    private String seatCode;

    private Boolean status;


}
