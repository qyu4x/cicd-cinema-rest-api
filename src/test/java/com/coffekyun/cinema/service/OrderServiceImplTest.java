package com.coffekyun.cinema.service;

import com.coffekyun.cinema.entity.Order;
import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testCreateOrderSuccess() {
        User dataUser = User.builder()
                .id("user-id")
                .name("kaguya chan")
                .email("kaguyachan@gmail.com")
                .password("secret")
                .build();

        Order order = Order.builder()
                .id("order-1")
                .user(dataUser)
                .build();
        Mockito.when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        Order orderResponse = orderService.createOrder(dataUser.getId());
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(order.getId(), orderResponse.getId());

        Mockito.verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testOrderByIdSuccess() {
        User dataUser = User.builder()
                .id("user-id")
                .name("kaguya chan")
                .email("kaguyachan@gmail.com")
                .password("secret")
                .build();

        Order order = Order.builder()
                .id("order-1")
                .user(dataUser)
                .build();

        Mockito.when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        Order orderResponse = orderService.findById(order.getId());
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(order.getId(), orderResponse.getId());

        Mockito.verify(orderRepository).findById(order.getId());
    }

    @Test
    void testOrderByIdFailedNotFound() {
        User dataUser = User.builder()
                .id("user-id")
                .name("kaguya chan")
                .email("kaguyachan@gmail.com")
                .password("secret")
                .build();

        Order order = Order.builder()
                .id("order-1")
                .user(dataUser)
                .build();

        Mockito.when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            orderService.findById(order.getId());
        });

        Mockito.verify(orderRepository).findById(order.getId());
    }
}