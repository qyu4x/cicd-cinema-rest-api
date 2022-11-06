package com.coffekyun.cinema.service;

import com.coffekyun.cinema.entity.Seat;
import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.model.dto.SeatRequest;
import com.coffekyun.cinema.model.dto.SeatResponse;
import com.coffekyun.cinema.repository.SeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatServiceImpl seatService;

    @Test
    void testAddSeatSuccess() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatCode("A1");

        Seat seat = Seat.builder()
                .id(seatRequest.getSeatCode())
                .seatCode(seatRequest.getSeatCode())
                .build();

        Mockito.when(seatRepository.existsById(seatRequest.getSeatCode()))
                .thenReturn(false);

        Mockito.when(seatRepository.save(any(Seat.class)))
                .thenReturn(seat);

        SeatResponse seatResponse = seatService.add(seatRequest);
        Assertions.assertNotNull(seatResponse);
        Assertions.assertEquals(seatRequest.getSeatCode(), seatResponse.getSeatCode());

        Mockito.verify(seatRepository).existsById(seatRequest.getSeatCode());
        Mockito.verify(seatRepository).save(any(Seat.class));
    }

    @Test
    void testAddSeatFailed() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatCode("A1");

        Mockito.when(seatRepository.existsById(seatRequest.getSeatCode()))
                .thenReturn(true);

        Assertions.assertThrows(DataAlreadyExistsException.class, () -> {
            seatService.add(seatRequest);
        });
    }
}