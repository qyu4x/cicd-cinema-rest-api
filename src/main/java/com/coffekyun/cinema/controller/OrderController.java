package com.coffekyun.cinema.controller;


import com.coffekyun.cinema.exception.GlobalExceptionHandler;
import com.coffekyun.cinema.model.dto.GlobalResponseHandler;
import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.model.dto.OrderResponse;
import com.coffekyun.cinema.service.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    private OrderDetailService orderDetailService;
    public OrderController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @Operation(
            summary = "order movies, seats and studios"
    )
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping(value = "/ticket")
    @ResponseBody
    public ResponseEntity<?> postRequestOrder(@Valid @RequestBody OrderRequest orderRequest) {
        log.info("#calling controller postRequestRegister");
        try {
            OrderResponse orderResponse = orderDetailService.createOrderDetail(orderRequest);
            log.info("#successfully place an order with order id {}, with user id {} ", orderResponse.getOrderId(), orderRequest.getUserId());
            return GlobalResponseHandler
                    .generateResponse("order successfully " , HttpStatus.OK, orderResponse);
        }catch (RuntimeException exception) {
            log.error("failed to place order for user id {}", orderRequest.getUserId());
            return GlobalExceptionHandler.dataAlreadyExistsHandler(exception.getMessage());
        }
    }
}
