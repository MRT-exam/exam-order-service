package com.mtgo.exam.orderservice.integration;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.message.OrderPlacedMessage;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.producer.OrderPlacedMessageProducer;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import com.mtgo.exam.orderservice.service.OrderService;
import com.mtgo.exam.orderservice.utils.JsonReader;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Testcontainers
@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
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
    OrderPlacedMessageProducer orderPlacedMessageProducer;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IOrderRepository orderRepository;

    OrderRequestDto orderRequestDto;
    List<OrderLineDto> orderLineDtoList;
    CustomerInfoDto customerInfoDto;
    Order order;
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

        orderRequestDto = OrderRequestDto.builder()
                .restaurantId("restaurant1")
                .orderDateTime(LocalDateTime.of(2023,12,5,14,18))
                .orderLineDtoList(orderLineDtoList)
                .comment("Uden birkes")
                .customerInfoDto(customerInfoDto)
                .build();
    }


    @Test
    void createOrder() {
        OrderDto orderDto = orderService.createOrder(orderRequestDto);

        assertNotNull(orderDto);
        assertEquals(OrderStatus.PENDING, orderDto.getStatus());
    }

    @Test
    void updateOrderShouldChangeStatusToAccepted() {
        OrderDto orderDto = orderService.updateOrderStatus(1, OrderStatus.ACCEPTED);
        assertEquals(OrderStatus.ACCEPTED, orderDto.getStatus());
    }

    @Test
    void updateOrderShouldChangeStatusToCanceled() {
        OrderDto orderDto = orderService.updateOrderStatus(1, OrderStatus.CANCELED);
        assertEquals(OrderStatus.CANCELED, orderDto.getStatus());
    }
}