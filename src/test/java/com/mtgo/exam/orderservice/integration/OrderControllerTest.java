package com.mtgo.exam.orderservice.integration;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.PlaceOrderRequestDto;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import com.mtgo.exam.orderservice.utils.JsonReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
    @Container
    private static final MySQLContainer mySQLContainer =
            new MySQLContainer("mysql");
    @Container
    private static final RabbitMQContainer rabbitMQContainer =
            new RabbitMQContainer("rabbitmq:management");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // MySQL Container
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);

        // RabbitMQ Container
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    IOrderRepository orderRepository;
    List<OrderLineDto> orderLineDtoList;
    CustomerInfoDto customerInfoDto;
    PlaceOrderRequestDto placeOrderRequestDto;
    Order order;
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + port + "/api/orders";
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

    @Test
    public void placeOrder() {
        given()
                .contentType(ContentType.JSON)
                .body(placeOrderRequestDto)
                .when()
                    .post("/new")
                .then()
                    .body("id", equalTo(2))
                    .body("status", equalTo("PENDING"));
    }
    @Test
    public void acceptOrder() {
        given()
                .when()
                    .put("/accept/2")
                .then()
                    .body("status", equalTo("ACCEPTED"));
    }
    @Test
    public void cancelOrder() {
        given()
                .when()
                    .put("/cancel/1")
                .then()
                    .body("status", equalTo("CANCELED"));
    }
}
