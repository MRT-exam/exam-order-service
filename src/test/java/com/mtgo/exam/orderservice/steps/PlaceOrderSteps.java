package com.mtgo.exam.orderservice.steps;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.service.OrderService;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
public class PlaceOrderSteps {

    private final OrderService orderService;
    private OrderRequestDto orderRequestDto;
    private Order order;

    @Given("the customer selects at least 1 Menu Item")
    public void customer_selects_min_one_item() {
        List<OrderLineDto> orderLineDtoList = new ArrayList<>();
        OrderLineDto orderLineDto = OrderLineDto.builder()
                .itemName("testItem1")
                .itemId("testItemId")
                .price(new BigDecimal(200))
                .quantity(2)
                .build();
        orderLineDtoList.add(orderLineDto);
        orderRequestDto = OrderRequestDto.builder()
                .restaurantId("restaurant1")
                .orderDateTime(LocalDateTime.of(2023, 12,2, 19,47))
                .orderLineDtoList(orderLineDtoList)
                .comment("test comment")
                .build();
    }

    @And("fills out the customer info fields")
    public void customer_fills_out_info_fields() {
        CustomerInfoDto customerInfoDto = CustomerInfoDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .phone(23457621)
                .address("testAddress")
                .build();
        orderRequestDto.setCustomerInfoDto(customerInfoDto);
    }

    @When("the customer places the order")
    public void customer_places_order() {
        order = orderService.createOrder(orderRequestDto);
    }

    @Then("a new order will be created with a PENDING Status")
    public void new_order_with_status_pending() {
        OrderStatus actual = order.getStatus();
        OrderStatus expected = OrderStatus.PENDING;
        assertEquals(expected, actual);
    }
}
