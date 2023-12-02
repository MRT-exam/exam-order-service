package com.mtgo.exam.orderservice.controller;

import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql");

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateNewOrderWithStatusPending() {
        Order order = restTemplate.postForObject("/api/orders/new", OrderRequestDto.class, Order.class);
        assertEquals(OrderStatus.PENDING,order.getStatus());
    }
}