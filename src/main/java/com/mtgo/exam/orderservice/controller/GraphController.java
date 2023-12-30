package com.mtgo.exam.orderservice.controller;

import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class GraphController {

    @Autowired
    private final OrderService orderService;

    public GraphController(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryMapping
    public OrderDto getOrderById(int orderId) {
        return orderService.getOrderById(orderId);
    }

    @QueryMapping
    public List<OrderDto> getAcceptedOrders(String restaurantId) {
        return orderService.getOrdersByStatus(restaurantId, OrderStatus.ACCEPTED);
    }
}
