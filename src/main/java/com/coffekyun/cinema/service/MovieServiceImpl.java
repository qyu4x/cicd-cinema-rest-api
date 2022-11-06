package com.coffekyun.cinema.service;

import com.coffekyun.cinema.entity.Movie;
import com.coffekyun.cinema.entity.Schedule;
import com.coffekyun.cinema.entity.Studio;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.model.dto.*;
import com.coffekyun.cinema.repository.MovieRepository;
import com.coffekyun.cinema.repository.ScheduleRepository;
import com.coffekyun.cinema.repository.StudioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    private final static Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private StudioRepository studioRepository;

    @Override
    public MovieResponse add(MovieRequest movieRequest) {
        log.info("carry out the process of adding films and studio");
        Movie movie = movieRequest.toFilm();
        String randomId = UUID
                .randomUUID().toString();
        movie.setId(randomId);
        Movie movieResponse =  movieRepository.save(movie);

        List<Schedule> resultToSchedule = movieRequest.toSchedule();
        resultToSchedule.stream()
                .forEach(schedule -> {
                    schedule.setId(UUID.randomUUID().toString());
                    schedule.setMovie(movieResponse);
                });
        List<Schedule> scheduleList =  scheduleRepository.saveAll(resultToSchedule);
        List<ScheduleResponse> schedules = new ArrayList<>();
        scheduleList.stream()
                .forEach(schedule -> {
                    ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                            .id(schedule.getId())
                            .movieId(schedule.getMovie().getId())
                            .showDate(schedule.getShowDate())
                            .startTime(schedule.getStartTime())
                            .endTime(schedule.getEndTime())
                            .price(schedule.getPrice())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(schedule.getUpdatedAt())
                            .build();
                    schedules.add(scheduleResponse);
                });

        List<Studio> restultToStudios = movieRequest.toStudio();
        restultToStudios.stream().forEach(studio -> {
            studio.setMovie(movieResponse);
        });
        List<Studio> studiosList =  studioRepository.saveAll(restultToStudios);
        List<StudioResponse> studios = new ArrayList<>();
        studiosList.stream()
                .forEach(studio -> {
                    StudioResponse studioResponse = StudioResponse.builder()
                            .id(studio.getId())
                            .movieId(studio.getMovie().getId())
                            .name(studio.getName())
                            .status(studio.getStatus())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(studio.getUpdatedAt())
                            .build();
                    studios.add(studioResponse);
                });


        return MovieResponse
                .builder()
                .id(movieResponse.getId())
                .title(movieResponse.getTitle())
                .description(movieResponse.getDescription())
                .schedules(schedules)
                .studios(studios)
                .startDate(movieResponse.getStartDate())
                .endDate(movieResponse.getEndDate())
                .showStatus(movieResponse.getShowStatus())
                .duration(movieResponse.getDuration())
                .genre(movieResponse.getGenre())
                .country(movieResponse.getCountry())
                .language(movieResponse.getLanguage())
                .createdAt(LocalDateTime.now())
                .updatedAt(movieResponse.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public MovieUpdateResponse update(MovieUpdateRequest title, String id) {
        log.info("update movie name based on id {} ", id);
        Optional<Movie> movieSearchResult = movieRepository.findById(id);
        if (movieSearchResult.isEmpty()) {
            log.info("movies with id {} not found", id);
            throw new DataNotFoundException("data with id " + id + "not found");
        } else {
            Date date = new Date();
            Integer affected =  movieRepository.updateTitleById(title.getTitle(), date, id);
            log.info("successfully update the movie with id {}", id);
            return MovieUpdateResponse.builder()
                    .rowAffected(affected)
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Override
    public void delete(String id) {
        if(movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            log.info("successfully delete movie with id {} ", id);
        } else {
            log.error("failed to delete movie by id {} ", id);
            throw new DataNotFoundException("data with id " + id + "not found");
        }
    }

    @Override
    public List<MovieIsShowingResponse> isShowing(Boolean status) {
        log.info("do take all the movies currently showing");
        List<Movie> movies =  movieRepository.findAllIfLive(status);
        List<MovieIsShowingResponse> movieResponses = new ArrayList<>();
        movies.stream()
                .forEach(movie -> {
                    MovieIsShowingResponse movieResponse  = MovieIsShowingResponse.builder()
                            .id(movie.getId())
                            .title(movie.getTitle())
                            .country(movie.getCountry())
                            .language(movie.getLanguage())
                            .genre(movie.getGenre())
                            .duration(movie.getDuration())
                            .startDate(movie.getStartDate())
                            .endDate(movie.getEndDate())
                            .showStatus(movie.getShowStatus())
                            .description(movie.getDescription())
                            .createdAt(movie.getCreatedAt())
                            .updatedAt(movie.getUpdatedAt())
                            .build();
                    movieResponses.add(movieResponse);
                });
        log.info("successful take all movies currently showing");
        return movieResponses;
    }

    @Override
    public MovieScheduleResponse showSchedule(String id) {
        log.info("do take all the movies by specific schedule id {} ", id);
        if(movieRepository.existsById(id)) {
            Movie movieResponses =  movieRepository.findScheduleByIdMovie(id);
            List<ScheduleResponse> scheduleResponses = new ArrayList<>();
            movieResponses.getSchedules().stream()
                    .forEach(schedule -> {
                        ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                                .id(schedule.getId())
                                .movieId(schedule.getMovie().getId())
                                .showDate(schedule.getShowDate())
                                .startTime(schedule.getStartTime())
                                .endTime(schedule.getEndTime())
                                .price(schedule.getPrice())
                                .createdAt(schedule.getCreatedAt())
                                .build();
                        scheduleResponses.add(scheduleResponse);
                    });

            List<Studio> studioList = studioRepository.findByStudioId(movieResponses.getId());
            List<StudioResponse> studioResponses = new ArrayList<>();

            studioList.stream()
                    .forEach(studio -> {
                        StudioResponse studioResponse = StudioResponse.builder()
                                .id(studio.getId())
                                .movieId(studio.getMovie().getId())
                                .name(studio.getName())
                                .status(studio.getStatus())
                                .createdAt(studio.getCreatedAt())
                                .build();
                        studioResponses.add(studioResponse);
                    });

            log.info("successful pick up movie schedule with id {} ", id);
            return MovieScheduleResponse.builder()
                    .id(movieResponses.getId())
                    .title(movieResponses.getTitle())
                    .genre(movieResponses.getGenre())
                    .country(movieResponses.getCountry())
                    .language(movieResponses.getLanguage())
                    .showStatus(movieResponses.getShowStatus())
                    .duration(movieResponses.getDuration())
                    .startDate(movieResponses.getStartDate())
                    .endDate(movieResponses.getEndDate())
                    .createdAt(movieResponses.getCreatedAt())
                    .updatedAt(movieResponses.getUpdatedAt())
                    .schedules(scheduleResponses)
                    .studios(studioResponses)
                    .build();
        } else {
            log.error("failed pick up movie schedule with id {} ", id);
            throw new DataNotFoundException("movie with id " + id + " not found");
        }
    }
}
