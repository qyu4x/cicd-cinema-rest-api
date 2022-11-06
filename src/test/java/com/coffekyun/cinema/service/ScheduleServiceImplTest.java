package com.coffekyun.cinema.service;

import com.coffekyun.cinema.entity.Schedule;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.model.dto.OrderDetailRequest;
import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.repository.ScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    void testFindScheduleByIdSuccess() {
        List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setScheduleId("schedule-anime");
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDetailRequests(orderDetailRequests);

        Schedule schedule = Schedule.builder()
                .id("schedule-anime")
                .price(new BigDecimal(30_000L))
                .build();


        Mockito.when(scheduleRepository.findById(orderDetailRequest.getScheduleId()))
                .thenReturn(Optional.of(schedule));

        List<Map<String, Object>> scheduleById = scheduleService.findScheduleById(orderRequest);
        Assertions.assertNotNull(scheduleById);

        Mockito.verify(scheduleRepository).findById(orderDetailRequest.getScheduleId());
    }

    @Test
    void testFindScheduleByIdFailed() {
        List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setScheduleId("schedule-anime");
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDetailRequests(orderDetailRequests);

        Mockito.when(scheduleRepository.findById(orderDetailRequest.getScheduleId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            scheduleService.findScheduleById(orderRequest);
        });

        Mockito.verify(scheduleRepository).findById(orderDetailRequest.getScheduleId());
    }
}