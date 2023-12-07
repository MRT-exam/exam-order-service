package com.mtgo.exam.orderservice.service;

import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;

import java.util.List;

public interface IOrderService {
    public List<Order> getOrdersByStatus(String restaurantId, OrderStatus status);

    public void updateOrderStatus(int orderId, OrderStatus status);

    public OrderDto createOrder(OrderRequestDto orderRequestDto);

}
