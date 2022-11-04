package com.coffekyun.cinema.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MovieScheduleResponse {
    private String id;

    private String title;

    private Boolean showStatus;

    private Integer duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String genre;

    private String country;

    private String language;

    private List<ScheduleResponse> schedules;

    private List<StudioResponse> studios;



}
