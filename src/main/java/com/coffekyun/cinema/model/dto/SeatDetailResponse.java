package com.coffekyun.cinema.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatDetailResponse {

    private String studioName;

    private String seatCode;

    private Boolean status;


}
