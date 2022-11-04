package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.model.dto.GlobalResponseHandler;
import com.coffekyun.cinema.model.dto.SeatRequest;
import com.coffekyun.cinema.model.dto.SeatResponse;
import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.exception.GlobalExceptionHandler;
import com.coffekyun.cinema.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/seat/")
public class SeatController {

    private final static Logger log = LoggerFactory.getLogger(SeatController.class);

    private SeatService seatService;
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @Operation(
            summary = "too add new seat"
    )
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<?> postRequestAddNewSeat(@Valid @RequestBody SeatRequest seatRequest) {
        log.info("#calling controller postRequestAddNewSeat");
        try {
            SeatResponse seatResponse =  seatService.add(seatRequest);
            log.info("#successfully added chair with code {}", seatRequest.getSeatCode());
            return GlobalResponseHandler
                    .generateResponse("successfully add seat with id "  + seatResponse.getId() , HttpStatus.OK, seatResponse);
        }catch (DataAlreadyExistsException exception) {
            log.info("#failed to add chair with name {}", seatRequest.getSeatCode());
            return GlobalExceptionHandler.dataAlreadyExistsHandler(exception.getMessage());
        }
    }
}
