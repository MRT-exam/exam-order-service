package com.mtgo.exam.orderservice.integration.service;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import com.mtgo.exam.orderservice.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
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

    OrderRequestDto orderRequestDto;
    List<OrderLineDto> orderLineDtoList;
    CustomerInfoDto customerInfoDto;
    @BeforeEach
    void setup() {
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
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void createOrder() {
        orderRequestDto = OrderRequestDto.builder()
                .restaurantId("restaurant1")
                .orderDateTime(LocalDateTime.of(2023,12,5,14,18))
                .orderLineDtoList(orderLineDtoList)
                .comment("Uden birkes")
                .customerInfoDto(customerInfoDto)
                .build();

        OrderDto orderDto = orderService.createOrder(orderRequestDto);

        assertNotNull(orderDto);
        assertEquals(OrderStatus.PENDING, orderDto.getStatus());
    }
}