package com.coffekyun.cinema.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class MovieUpdateResponse {

    private Integer rowAffected;

    private LocalDateTime updatedAt;

}
