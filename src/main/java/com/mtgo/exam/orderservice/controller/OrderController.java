package com.mtgo.exam.orderservice.controller;

import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.message.OrderPlacedMessage;
import com.mtgo.exam.orderservice.producer.OrderPlacedMessageProducer;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderPlacedMessageProducer orderPlacedMessageProducer;

    @PostMapping("/new")
    public OrderDto placeOrder(@RequestBody OrderRequestDto orderRequestDto) {

        OrderDto orderDto = orderService.createOrder(orderRequestDto);

        OrderPlacedMessage orderPlacedMessage = OrderPlacedMessage.builder()
                .orderId(orderDto.getId())
                .orderNumber(orderDto.getOrderNumber())
                .restaurantId(orderDto.getRestaurantId())
                .build();

        orderPlacedMessageProducer.sendOrderPlacedMessage(orderPlacedMessage);

        return orderDto;
    }

    @PutMapping("/accept/${orderId}")
    public String acceptOrder(@RequestParam int orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.ACCEPTED);
        return "Order has been accepted";
    }

    @PutMapping("/cancel/${orderId}")
    public String cancelOrder(@RequestParam int orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
        return "Order has been accepted";
    }


}
