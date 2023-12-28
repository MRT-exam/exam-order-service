package com.mtgo.exam.orderservice.controller;

import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.message.OrderPlacedMessage;
import com.mtgo.exam.orderservice.producer.OrderPlacedMessageProducer;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.service.OrderService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderPlacedMessageProducer orderPlacedMessageProducer;

    @PostMapping("/new")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        if (orderRequestDto == null) {
            throw new BadRequestException("Request body cannot be null");
        }

        OrderDto orderDto = orderService.createOrder(orderRequestDto);

        OrderPlacedMessage orderPlacedMessage = OrderPlacedMessage.builder()
                .orderId(orderDto.getId())
                .orderNumber(orderDto.getOrderNumber())
                .restaurantId(orderDto.getRestaurantId())
                .build();

        orderPlacedMessageProducer.sendOrderPlacedMessage(orderPlacedMessage);

        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PutMapping("/accept/{orderId}")
    public OrderDto acceptOrder(@PathVariable int orderId) {
        OrderDto orderDto = orderService.updateOrderStatus(orderId, OrderStatus.ACCEPTED);
        return orderDto;
    }

    @PutMapping("/cancel/{orderId}")
    public OrderDto cancelOrder(@PathVariable int orderId) {
        OrderDto orderDto = orderService.updateOrderStatus(orderId, OrderStatus.CANCELED);
        return orderDto;
    }


}
