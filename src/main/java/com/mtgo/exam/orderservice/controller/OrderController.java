package com.mtgo.exam.orderservice.controller;

import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/new")
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {
        OrderDto orderDto = orderService.createOrder(orderRequestDto);
        rabbitTemplate.convertAndSend("", "order-submission", orderDto);
        return orderDto;
    }
}
