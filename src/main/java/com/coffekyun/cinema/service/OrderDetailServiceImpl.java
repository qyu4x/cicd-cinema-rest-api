package com.coffekyun.cinema.service;

import com.coffekyun.cinema.entity.*;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.model.dto.OrderDetailResponse;
import com.coffekyun.cinema.model.dto.OrderRequest;
import com.coffekyun.cinema.model.dto.OrderResponse;
import com.coffekyun.cinema.repository.OrderDetailRepository;
import com.coffekyun.cinema.repository.OrderRepository;
import com.coffekyun.cinema.repository.SeatDetailRepository;
import com.coffekyun.cinema.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final Logger log = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SeatDetailService seatDetailService;
    @Autowired
    private SeatDetailRepository seatDetailRepository;


    @Transactional
    @Override
    public OrderResponse createOrderDetail(OrderRequest orderRequest) {
        log.info("carry out the process of making detailed orders with user id {} ", orderRequest.getUserId());
        if (userRepository.existsById(orderRequest.getUserId())) {
            List<Map<String, Object>> orderDetails = scheduleService.findScheduleById(orderRequest);
            Order order = orderService.createOrder(orderRequest.getUserId());

            orderDetails.forEach(orderDetail -> {
                insertIntoTableOrderDetail(orderDetail, order);
            });

            BigDecimal totalPrice = getTotalPriceById(order);
            orderRepository.updateTotalPriceById(totalPrice, order.getId());
            List<OrderDetail> dataOrderDetails = orderDetailRepository.findOrderDetailByOrderId(order.getId());
            if (seatDetailService.checkSeatAvailability(orderRequest)
                    && seatDetailService.checkNumberOfSeat(dataOrderDetails, orderRequest)) {
                log.info("do update data order id, order req, and schedule into table schedule detail for user id {}", orderRequest.getUserId() );
                // update data order id, order req, and schedule into table schedule detail
                seatDetailRepository.updateSeatDetail(order, dataOrderDetails, orderRequest);
                log.info("successfuly update data order id, order req, and schedule into table schedule detail for user id {} ", orderRequest.getUserId());

            }

            return getResponse(order, totalPrice);
        } else {
            log.warn("user with id {} is not found", orderRequest.getUserId());
            throw new DataNotFoundException("Oops user with id " + orderRequest.getUserId() + "not found ");
        }

    }

    @Override
    public void insertIntoTableOrderDetail(Map<String, Object> orderDetailScheduleAndQuantity, Order dataOrder) {
        String orderId = dataOrder.getId();
        Integer quantity = (Integer) orderDetailScheduleAndQuantity.get("quantity");
        Schedule dataSchedule = (Schedule) orderDetailScheduleAndQuantity.get("schedule");
        String scheduleId = dataSchedule.getId();
        BigDecimal scheduleMoviePrice = dataSchedule.getPrice();

        OrderDetail orderDetail = OrderDetail.builder()
                .quantity(quantity)
                .price(scheduleMoviePrice)
                .order(Order.builder()
                        .id(orderId)
                        .build())
                .schedule(Schedule.builder()
                        .id(scheduleId)
                        .build())
                .build();
        orderDetailRepository.addOrderDetail(orderDetail);
    }

    @Override
    public BigDecimal getTotalPriceById(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailByOrderId(order.getId());
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        orderDetails.stream()
                .forEach(orderDetail -> {
                    Double totalPerLine = orderDetail.getQuantity() * orderDetail.getPrice().doubleValue();
                    totalPrice.updateAndGet(base -> base + totalPerLine);
                });

        return new BigDecimal(totalPrice.toString());
    }

    @Override
    public List<OrderDetailResponse> getOrderDetail(String id) {
        List<OrderDetail> orderResponse = orderDetailRepository.findOrderDetailByOrderId(id);
        Locale indonesia = new Locale("id", "ID");
        Currency currency = Currency.getInstance(indonesia);
        String currencyCode = currency.getCurrencyCode();
        String currencySymbol = currency.getSymbol(indonesia);

        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();

        orderResponse.forEach(orderDetail -> {
            OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                    .scheduleId(orderDetail.getSchedule().getId())
                    .title(orderDetail.getSchedule().getMovie().getTitle())
                    .showDate(orderDetail.getSchedule().getShowDate())
                    .startTime(orderDetail.getSchedule().getStartTime())
                    .endTime(orderDetail.getSchedule().getEndTime())
                    .price(orderDetail.getPrice())
                    .currencyCode(currencyCode)
                    .currencySymbol(currencySymbol)
                    .quantity(orderDetail.getQuantity())
                    .build();
            orderDetailResponses.add(orderDetailResponse);
        });

        return orderDetailResponses;
    }

    @Override
    public OrderResponse getResponse(Order order, BigDecimal totalPrice) {
        OrderResponse orderResponse = new OrderResponse();
        List<OrderDetailResponse> orderDetail = getOrderDetail(order.getId());

        Locale indonesia = new Locale("id", "ID");
        Currency currency = Currency.getInstance(indonesia);
        String currencyCode = currency.getCurrencyCode();
        String currencySymbol = currency.getSymbol(indonesia);

        orderResponse.setOrderId(order.getId());
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setOrderDetails(orderDetail);
        orderResponse.setCurrencyCode(currencyCode);
        orderResponse.setCurrencySymbol(currencySymbol);

        return orderResponse;
    }
}
