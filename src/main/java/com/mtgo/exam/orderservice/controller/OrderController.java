package com.mtgo.exam.orderservice.controller;

import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.message.OrderPlacedMessage;
import com.mtgo.exam.orderservice.producer.OrderPlacedMessageProducer;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.PlaceOrderRequestDto;
import com.mtgo.exam.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderPlacedMessageProducer orderPlacedMessageProducer;

    @PostMapping("/new")
    public ResponseEntity<OrderDto> placeOrder(@Valid @RequestBody PlaceOrderRequestDto placeOrderRequestDto) {

        OrderDto orderDto = orderService.createOrder(placeOrderRequestDto);

        OrderPlacedMessage orderPlacedMessage = OrderPlacedMessage.builder()
                .orderId(orderDto.getId())
                .orderNumber(orderDto.getOrderNumber())
                .restaurantId(orderDto.getRestaurantId())
                .build();

        orderPlacedMessageProducer.sendOrderPlacedMessage(orderPlacedMessage);

        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}/pending")
    public ResponseEntity<List<OrderDto>> getPendingOrders(@PathVariable String restaurantId) {
        List<OrderDto> orderDtos = orderService.getOrdersByStatus(restaurantId, OrderStatus.PENDING);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}/accepted")
    public ResponseEntity<List<OrderDto>> getAcceptedOrders(@PathVariable String restaurantId) {
        List<OrderDto> orderDtos = orderService.getOrdersByStatus(restaurantId, OrderStatus.ACCEPTED);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @PutMapping("/accept/{orderId}")
    public ResponseEntity<OrderDto> acceptOrder(@PathVariable int orderId) {
        OrderDto orderDto = orderService.updateOrderStatus(orderId, OrderStatus.ACCEPTED);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable int orderId) {
        OrderDto orderDto = orderService.updateOrderStatus(orderId, OrderStatus.CANCELED);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

}
