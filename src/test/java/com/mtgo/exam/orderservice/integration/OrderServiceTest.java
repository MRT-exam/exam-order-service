package com.mtgo.exam.orderservice.integration;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.PlaceOrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import com.mtgo.exam.orderservice.service.OrderService;
import com.mtgo.exam.orderservice.utils.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Testcontainers
@SpringBootTest
class OrderServiceTest {
    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private IOrderRepository orderRepository;

    PlaceOrderRequestDto placeOrderRequestDto;
    List<OrderLineDto> orderLineDtoList;
    CustomerInfoDto customerInfoDto;
    Order order;
    @Transactional
    @BeforeEach
    void setup() {
        order = JsonReader.readOrderFromJson();
        orderRepository.save(order);
        orderLineDtoList = new ArrayList<>();
        orderLineDtoList.add(
                OrderLineDto.builder()
                        .itemId("mongoDbItemId")
                        .itemName("Kage")
                        .price(new BigDecimal(200.00))
                        .quantity(3)
                        .build()
        );

        customerInfoDto = customerInfoDto.builder()
                .userId(1)
                .firstName("Jane")
                .lastName("Doe")
                .phone(43547690)
                .address("Elm Street 4")
                .build();

        placeOrderRequestDto = PlaceOrderRequestDto.builder()
                .restaurantId("restaurant1")
                .orderDateTime(LocalDateTime.of(2023,12,5,14,18))
                .orderLineDtoList(orderLineDtoList)
                .comment("Uden birkes")
                .customerInfoDto(customerInfoDto)
                .build();
    }
    @AfterEach
    void cleanup() {
        orderRepository.deleteAll();
    }

    @Transactional
    @Rollback
    @Test
    void updateOrderShouldChangeStatusToCanceled() {
        OrderDto orderDto = orderService.updateOrderStatus(1, OrderStatus.CANCELED);
        assertEquals(OrderStatus.CANCELED.getCode(), orderDto.getStatus());
    }

    @Transactional
    @Rollback
    @Test
    void updateOrderShouldChangeStatusToAccepted() {
        OrderDto orderDto = orderService.updateOrderStatus(4, OrderStatus.ACCEPTED);
        assertEquals(OrderStatus.ACCEPTED.getCode(), orderDto.getStatus());
    }
    @Transactional
    @Rollback
    @Test
    void createOrder() {
        OrderDto orderDto = orderService.createOrder(placeOrderRequestDto);
        assertNotNull(orderDto);
        assertEquals(OrderStatus.PENDING.getCode(), orderDto.getStatus());
    }
}