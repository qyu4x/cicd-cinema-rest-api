package com.coffekyun.cinema.service;

import com.coffekyun.cinema.entity.OrderDetail;
import com.coffekyun.cinema.entity.Seat;
import com.coffekyun.cinema.entity.SeatDetail;
import com.coffekyun.cinema.entity.Studio;
import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.exception.ValidationException;
import com.coffekyun.cinema.model.dto.*;
import com.coffekyun.cinema.repository.SeatDetailRepository;
import com.coffekyun.cinema.repository.SeatRepository;
import com.coffekyun.cinema.repository.StudioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SeatDetailServiceImplTest {

    @Mock
    private SeatDetailRepository seatDetailRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private StudioRepository studioRepository;

    @InjectMocks
    private SeatDetailServiceImpl seatDetailService;

    @Test
    void testAddSeatAndStudioSuccess() {
        SeatDetailRequest seatDetailRequest = new SeatDetailRequest();
        seatDetailRequest.setSeatCode("A1");
        seatDetailRequest.setStudioName("SAGIRI");
        seatDetailRequest.setStatus(true);

        Seat seat = Seat.builder()
                .id("A1")
                .seatCode("A1")
                .build();

        Studio studio = Studio.builder()
                .id("SAGIRI")
                .build();

        SeatDetail seatDetail = SeatDetail.builder()
                .studio(studio)
                .seat(seat)
                .status(true)
                .build();

        Mockito.when(seatRepository.findById(seatDetailRequest.getSeatCode()))
                .thenReturn(Optional.of(seat));

        Mockito.when(studioRepository.findById(seatDetailRequest.getStudioName()))
                .thenReturn(Optional.of(studio));

        Mockito.when(seatDetailRepository.findByStudioAndSeat(any(Studio.class), any(Seat.class)))
                .thenReturn(null);

        SeatDetailResponse seatDetailResponse = seatDetailService.addSeatAndStudio(seatDetailRequest);
        Assertions.assertEquals(seatDetail.getSeat().getId(), seatDetailResponse.getSeatCode());
        Assertions.assertEquals(seatDetail.getStudio().getName(), seatDetailResponse.getStudioName());

        Mockito.verify(seatDetailRepository).findByStudioAndSeat(any(Studio.class), any(Seat.class));
        Mockito.verify(studioRepository).findById(seatDetailRequest.getStudioName());
        Mockito.verify(seatRepository).findById(seatDetailRequest.getSeatCode());

    }


    @Test
    void testAddSeatAndStudioFailedDataHasBeenEntered() {
        SeatDetailRequest seatDetailRequest = new SeatDetailRequest();
        seatDetailRequest.setSeatCode("A1");
        seatDetailRequest.setStudioName("SAGIRI");
        seatDetailRequest.setStatus(true);

        Seat seat = Seat.builder()
                .id("A1")
                .seatCode("A1")
                .build();

        Studio studio = Studio.builder()
                .id("SAGIRI")
                .build();

        SeatDetail seatDetail = SeatDetail.builder()
                .studio(studio)
                .seat(seat)
                .status(true)
                .build();

        Mockito.when(seatRepository.findById(seatDetailRequest.getSeatCode()))
                .thenReturn(Optional.of(seat));

        Mockito.when(studioRepository.findById(seatDetailRequest.getStudioName()))
                .thenReturn(Optional.of(studio));

        Mockito.when(seatDetailRepository.findByStudioAndSeat(any(Studio.class), any(Seat.class)))
                .thenReturn(seatDetail);

        Assertions.assertThrows(DataAlreadyExistsException.class, () -> {
            seatDetailService.addSeatAndStudio(seatDetailRequest);
        });

        Mockito.verify(seatDetailRepository).findByStudioAndSeat(any(Studio.class), any(Seat.class));
        Mockito.verify(studioRepository).findById(seatDetailRequest.getStudioName());
        Mockito.verify(seatRepository).findById(seatDetailRequest.getSeatCode());
    }

    @Test
    void testAddSeatAndStudioFailedDataNotFound() {
        SeatDetailRequest seatDetailRequest = new SeatDetailRequest();
        seatDetailRequest.setSeatCode("A1");
        seatDetailRequest.setStudioName("SAGIRI");
        seatDetailRequest.setStatus(true);


        Mockito.when(seatRepository.findById(seatDetailRequest.getSeatCode()))
                .thenReturn(Optional.empty());

        Mockito.when(studioRepository.findById(seatDetailRequest.getStudioName()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            seatDetailService.addSeatAndStudio(seatDetailRequest);
        });

        Mockito.verify(studioRepository).findById(seatDetailRequest.getStudioName());
        Mockito.verify(seatRepository).findById(seatDetailRequest.getSeatCode());
    }

    @Test
    void testGetSeatIfAvailableSuccess() {
        Seat seat = Seat.builder()
                .id("A1")
                .seatCode("A1")
                .build();

        Studio studio = Studio.builder()
                .id("SAGIRI")
                .name("SAGIRI")
                .build();

        List<SeatDetail> seatDetails = new ArrayList<>();
        SeatDetail seatDetail = SeatDetail.builder()
                .studio(studio)
                .seat(seat)
                .status(true)
                .build();
        seatDetails.add(seatDetail);

        Mockito.when(seatDetailRepository.findByStatus(true))
                .thenReturn(seatDetails);

        List<SeatDetailResponse> seatIfAvailable = seatDetailService.getSeatIfAvailable(true);
        Assertions.assertNotNull(seatIfAvailable);

        Mockito.verify(seatDetailRepository).findByStatus(true);
    }

    @Test
    void testCheckSeatFailedDataNotFound() {
        List<OrderSeatRequest> orderSeatRequests = new ArrayList<>();
        OrderSeatRequest orderSeatRequest = new OrderSeatRequest();
        orderSeatRequest.setSeatCode("A1");
        orderSeatRequest.setStudioName("SAGIRI");
        orderSeatRequests.add(orderSeatRequest);

        List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrderSeatRequests(orderSeatRequests);
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDetailRequests(orderDetailRequests);

        Seat seat = Seat.builder()
                .id("A1")
                .seatCode("A1")
                .build();

        Studio studio = Studio.builder()
                .id("SAGIRI")
                .name("SAGIRI")
                .build();

        List<SeatDetail> seatDetails = new ArrayList<>();
        SeatDetail seatDetail = SeatDetail.builder()
                .studio(studio)
                .seat(seat)
                .status(true)
                .build();
        seatDetails.add(seatDetail);

        Mockito.when(seatDetailRepository.findBySeatCodeAndStudioName(seat.getSeatCode(), studio.getName()))
                .thenReturn(null);

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            seatDetailService.checkSeatAvailability(orderRequest);
        });

        Mockito.verify(seatDetailRepository).findBySeatCodeAndStudioName(seat.getSeatCode(), studio.getName());
    }

    @Test
    void testCheckSeatSuccess() {
        List<OrderSeatRequest> orderSeatRequests = new ArrayList<>();
        OrderSeatRequest orderSeatRequest = new OrderSeatRequest();
        orderSeatRequest.setSeatCode("A1");
        orderSeatRequest.setStudioName("SAGIRI");
        orderSeatRequests.add(orderSeatRequest);

        List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrderSeatRequests(orderSeatRequests);
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDetailRequests(orderDetailRequests);

        Seat seat = Seat.builder()
                .id("A1")
                .seatCode("A1")
                .build();

        Studio studio = Studio.builder()
                .id("SAGIRI")
                .name("SAGIRI")
                .build();

        List<SeatDetail> seatDetails = new ArrayList<>();
        SeatDetail seatDetail = SeatDetail.builder()
                .studio(studio)
                .seat(seat)
                .status(true)
                .build();
        seatDetails.add(seatDetail);

        Mockito.when(seatDetailRepository.findBySeatCodeAndStudioName(seat.getSeatCode(), studio.getName()))
                .thenReturn(seatDetails);

        Boolean status = seatDetailService.checkSeatAvailability(orderRequest);
        Assertions.assertTrue(status);

        Mockito.verify(seatDetailRepository, Mockito.times(2))
                .findBySeatCodeAndStudioName(seat.getSeatCode(), studio.getName());
    }

    @Test
    void testCheckSeatFailedStudioOrSeatHasBeenOrdered() {
        List<OrderSeatRequest> orderSeatRequests = new ArrayList<>();
        OrderSeatRequest orderSeatRequest = new OrderSeatRequest();
        orderSeatRequest.setSeatCode("A1");
        orderSeatRequest.setStudioName("SAGIRI");
        orderSeatRequests.add(orderSeatRequest);

        List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrderSeatRequests(orderSeatRequests);
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDetailRequests(orderDetailRequests);

        Seat seat = Seat.builder()
                .id("A1")
                .seatCode("A1")
                .build();

        Studio studio = Studio.builder()
                .id("SAGIRI")
                .name("SAGIRI")
                .build();

        List<SeatDetail> seatDetails = new ArrayList<>();
        SeatDetail seatDetail = SeatDetail.builder()
                .studio(studio)
                .seat(seat)
                .status(false)
                .build();
        seatDetails.add(seatDetail);

        Mockito.when(seatDetailRepository.findBySeatCodeAndStudioName(seat.getSeatCode(), studio.getName()))
                .thenReturn(seatDetails);

        Assertions.assertThrows(DataAlreadyExistsException.class, () -> {
            seatDetailService.checkSeatAvailability(orderRequest);
        });

        Mockito.verify(seatDetailRepository, Mockito.times(2))
                .findBySeatCodeAndStudioName(seat.getSeatCode(), studio.getName());
    }

    @Test
    void testCheckNumberOfSeatSuccess() {
        List<OrderSeatRequest> orderSeatRequests = new ArrayList<>();
        OrderSeatRequest orderSeatRequest = new OrderSeatRequest();
        orderSeatRequest.setSeatCode("A1");
        orderSeatRequest.setStudioName("SAGIRI");
        orderSeatRequests.add(orderSeatRequest);

        List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrderSeatRequests(orderSeatRequests);
        orderDetailRequest.setQuantity(1);
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDetailRequests(orderDetailRequests);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .quantity(1)
                .build();
        orderDetails.add(orderDetail);

        Boolean status = seatDetailService.checkNumberOfSeat(orderDetails, orderRequest);
        Assertions.assertTrue(status);
    }

    @Test
    void testCheckNumberOfSeatFailed() {
        List<OrderSeatRequest> orderSeatRequests = new ArrayList<>();
        OrderSeatRequest orderSeatRequest = new OrderSeatRequest();
        orderSeatRequest.setSeatCode("A1");
        orderSeatRequest.setStudioName("SAGIRI");
        orderSeatRequests.add(orderSeatRequest);

        List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrderSeatRequests(orderSeatRequests);
        orderDetailRequest.setQuantity(1);
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDetailRequests(orderDetailRequests);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .quantity(2)
                .build();
        orderDetails.add(orderDetail);

        Assertions.assertThrows(ValidationException.class, () -> {
            seatDetailService.checkNumberOfSeat(orderDetails, orderRequest);
        });
    }
}