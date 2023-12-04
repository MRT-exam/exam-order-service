package com.mtgo.exam.orderservice.integration.service;

import com.mtgo.exam.orderservice.base.BaseServiceTest;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import com.mtgo.exam.orderservice.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest extends BaseServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    IOrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void createOrder() {
        OrderRequestDto orderRequestDto = this.createOrderRequestDto();
        OrderDto orderDto =orderService.createOrder(orderRequestDto);

        assertNotNull(orderDto);
        assertNotNull(orderDto.getId());

        assertEquals(OrderStatus.PENDING, orderDto.getStatus());
    }
}