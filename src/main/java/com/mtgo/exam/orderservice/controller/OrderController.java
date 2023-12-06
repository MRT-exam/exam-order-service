package com.mtgo.exam.orderservice.controller;

//import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mtgo.exam.orderservice.config.OrderPlacedMessage;
import com.mtgo.exam.orderservice.config.MQConfig;
import com.mtgo.exam.orderservice.config.MessagePublisher;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.service.OrderService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    //private final RabbitTemplate rabbitTemplate;

    private final MessagePublisher messagePublisher;

    @PostMapping("/new")
    public OrderDto placeOrder(@RequestBody OrderRequestDto orderRequestDto) {

        OrderDto orderDto = orderService.createOrder(orderRequestDto);
        log.info(String.valueOf(orderDto.getOrderDateTime()));
        //rabbitTemplate.convertAndSend("", "order-submission", orderDto);
        OrderPlacedMessage orderPlacedMessage = OrderPlacedMessage.builder()
                .orderId(orderDto.getId())
                .orderNumber(orderDto.getOrderNumber())
                .restaurantId(orderDto.getRestaurantId())
                .build();

        messagePublisher.publishMessage(orderPlacedMessage);

        return orderDto;
    }


}
