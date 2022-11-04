package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.exception.GlobalExceptionHandler;
import com.coffekyun.cinema.model.dto.GlobalResponseHandler;
import com.coffekyun.cinema.model.dto.SeatDetailRequest;
import com.coffekyun.cinema.model.dto.SeatDetailResponse;
import com.coffekyun.cinema.service.SeatDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/seat-details/")
public class SeatDetailController {

    private final static Logger log = LoggerFactory.getLogger(SeatDetailController.class);
    private SeatDetailService seatDetailService;
    public SeatDetailController(SeatDetailService seatDetailService) {
        this.seatDetailService = seatDetailService;
    }

    @Operation(
            summary = "to add seats and studio details from existing seats and studios"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<?> postRequestAddNewSeatDetails(@Valid @RequestBody SeatDetailRequest seatDetailRequest) {
        log.info("#calling controller postRequestAddNewSeatDetails");
        try {
            SeatDetailResponse seatDetailResponse =  seatDetailService.addSeatAndStudio(seatDetailRequest);
            log.info("#successfully added a new seat and studio pair, with seat {} and studio {} ", seatDetailRequest.getSeatCode(), seatDetailRequest.getStudioName());
            return GlobalResponseHandler
                    .generateResponse("successfully add seat and studio" , HttpStatus.OK, seatDetailResponse);
        }catch (DataAlreadyExistsException | DataNotFoundException exception) {
            log.info("#failed added a new seat and studio pair, with seat {} and studio {} ", seatDetailRequest.getSeatCode(), seatDetailRequest.getStudioName());
            return GlobalExceptionHandler.dataAlreadyExistsHandler(exception.getMessage());
        }
    }

    @Operation(
            summary = "to see which seat and studio pairs are still available"
    )
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/available")
    @ResponseBody
    public ResponseEntity<?> requestShowMovieSchedule(@RequestParam Boolean status) {
        log.info("#calling controller requestShowMovieSchedule");
        List<SeatDetailResponse> seatDetailResponses = seatDetailService.getSeatIfAvailable(status);
        log.info("#success in getting all available seat data");
        return GlobalResponseHandler
                .generateResponse("successfully to get all seats", HttpStatus.OK, seatDetailResponses);
    }

}
