package com.mtgo.exam.orderservice.service;

import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService{
    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return null;
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        return null;
    }
}
