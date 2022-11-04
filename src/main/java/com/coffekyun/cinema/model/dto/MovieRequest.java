package com.coffekyun.cinema.model.dto;

import com.coffekyun.cinema.entity.Movie;
import com.coffekyun.cinema.entity.Schedule;
import com.coffekyun.cinema.entity.Studio;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MovieRequest {

    @NotEmpty(message = "The full title is required.")
    @Size(min = 2, max = 100, message = "The length of full title must be between 2 and 100 characters.")
    private String title;

    private Boolean showStatus;

    @NotNull(message = "positive number value is required")
    @Min(value=24, message="positive number, min 24 is required")
    private Integer duration;

    @NotNull(message = "The date of start date is required.")
    private LocalDate startDate;

    @NotNull(message = "The date of end date is required.")
    private LocalDate endDate;

    @NotEmpty(message = "The description is required.")
    private String description;

    @NotEmpty(message = "The genre is required.")
    private String genre;

    @NotEmpty(message = "The country is required.")
    private String country;

    @NotEmpty(message = "The language is required.")
    private String language;

    private List<ScheduleRequest> schedules;

    private List<StudioRequest> studios;



    public Movie toFilm() {
        return Movie.builder()
                .title(this.title)
                .showStatus(this.showStatus)
                .duration(this.duration)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .genre(this.genre)
                .country(this.country)
                .language(this.language)
                .build();
    }

    public List<Schedule> toSchedule() {
        List<Schedule> resultSchedules = new ArrayList<>();
        schedules.stream().forEach(schedule -> {
            Schedule tempSchedule = Schedule.builder()
                    .price(schedule.getPrice())
                    .showDate(schedule.getShowDate())
                    .endTime(schedule.getEndTime())
                    .startTime(schedule.getStartTime())
                    .build();

            resultSchedules.add(tempSchedule);
        });

        return resultSchedules;
    }

    public List<Studio> toStudio() {
        List<Studio> resultStudio = new ArrayList<>();
        studios.stream().forEach(studio -> {
            Studio tempStudio = Studio.builder()
                    .id(studio.getName())
                    .name(studio.getName())
                    .status(studio.getStatus())
                    .build();

            resultStudio.add(tempStudio);
        });

        return resultStudio;
    }

}
