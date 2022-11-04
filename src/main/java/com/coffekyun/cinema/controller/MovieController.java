package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.exception.GlobalExceptionHandler;
import com.coffekyun.cinema.model.dto.*;
import com.coffekyun.cinema.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/movie/")
public class MovieController {

    private final static Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Operation(
            summary = "add movie data, schedule, and studio"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<?> postRequestAddNewMovie(@Valid @RequestBody MovieRequest movieRequest) {
        log.info("#calling controller postRequestAddNewMovie");
        final MovieResponse movieResponse =  movieService.add(movieRequest);
        log.info("#successfully added movie with title {} ", movieRequest.getTitle());
        return GlobalResponseHandler.generateResponse(
                "successfully added movie with id "  + movieResponse.getId(),HttpStatus.OK, movieResponse
        );
    }

    @Operation(
            summary = "update movie title by id"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public ResponseEntity<?> postRequestChangeMovie(@Valid @RequestBody MovieUpdateRequest movieUpdateRequest, @PathVariable("id") String id) {
        log.info("#calling controller postRequestChangeMovie");
        try {
            final MovieUpdateResponse movieUpdateResponse =  movieService.update(movieUpdateRequest, id);
            log.info("#successfully update movie with id {} ", id);
            return GlobalResponseHandler
                    .generateResponse("successfully update movie with id "  + id ,HttpStatus.OK, movieUpdateResponse);
        }catch (DataNotFoundException exception) {
            log.error("failed to update movie with id {} ", id);
            return GlobalExceptionHandler.dataNotFoundHandler(exception.getMessage());
        }
    }

    @Operation(
            summary = "soft delete movie data by id"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> requestDeleteMovie(@PathVariable("id") String id) {
        log.info("#calling controller requestDeleteMovie");
        try {
            movieService.delete(id);
            log.info("#successfully delete movie with id {} ", id);
            return GlobalResponseHandler
                    .generateResponse("successfully delete data with id "  + id ,HttpStatus.OK, null);
        }catch (DataNotFoundException exception) {
            log.error("failed to delete movie with id {} ", id);
            return GlobalExceptionHandler.dataNotFoundHandler(exception.getMessage());
        }
    }

    @Operation(
            summary = "showing currently showing movies"
    )
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/show")
    @ResponseBody
    public ResponseEntity<?> requestShowMovieSchedule(@RequestParam Boolean status) {
        log.info("#calling controller requestShowMovieSchedule");
        List<MovieIsShowingResponse> onShowResponse = movieService.isShowing(status);
        log.info("#sucess taking all currently showing movies");
        return GlobalResponseHandler
                .generateResponse("success taking all the films that are currently showing", HttpStatus.OK, onShowResponse);
    }

    @Operation(
            summary = "show specific movie schedule based on movie id"
    )
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/schedule/{id}")
    @ResponseBody
    public ResponseEntity<?> requestShowMovieScheduleBasedOnMovie(@PathVariable String id) {
        log.info("#calling controller requestShowMovieScheduleBasedOnMovie");
        try {
            MovieScheduleResponse movieScheduleResponse = movieService.showSchedule(id);
            log.info("#successful pick up movie schedule with id {}", id);
            return GlobalResponseHandler
                    .generateResponse("successfully get movie schedule with id "  + id ,HttpStatus.OK, movieScheduleResponse);
        }catch (DataNotFoundException exception) {
            log.error("failed to pick up movie schedule with id {} ", id);
            return GlobalExceptionHandler.dataNotFoundHandler(exception.getMessage());
        }
    }

}
