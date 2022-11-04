package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.SeatRequest;
import com.coffekyun.cinema.model.dto.SeatResponse;
import com.coffekyun.cinema.entity.Seat;
import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService{

    private final static Logger log = LoggerFactory.getLogger(SeatServiceImpl.class);

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public SeatResponse add(SeatRequest seatRequest) {
        log.info("carry out the process of adding seats with seat code {} ", seatRequest.getSeatCode());
        Seat seat =  seatRequest.toSeat();
        if(seatRepository.existsById(seat.getId())) {
            log.warn("seat with code {} is already exist", seat.getSeatCode());
            throw new DataAlreadyExistsException( "There is already a seat with a code seat :" + seat.getSeatCode());
        } else {
            log.info("succesfully added seat with seat code {} ", seatRequest.getSeatCode());
            Seat seatResponse = seatRepository.save(seat);
            return SeatResponse.builder()
                    .id(seatResponse.getId())
                    .seatCode(seatResponse.getSeatCode())
                    .build();
        }
    }
}
