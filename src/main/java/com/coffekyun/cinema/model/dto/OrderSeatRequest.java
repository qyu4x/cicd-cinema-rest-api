package com.coffekyun.cinema.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderSeatRequest {

    @NotEmpty(message = "The seat code is required.")
    private String seatCode;

    @NotEmpty(message = "The seat code is required.")
    private String studioName;


}
