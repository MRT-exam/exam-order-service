package com.mtgo.exam.orderservice.service;

import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.PlaceOrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;

import java.util.List;

public interface IOrderService {
    public List<OrderDto> getOrdersByStatus(String restaurantId, OrderStatus status);

    public OrderDto updateOrderStatus(int orderId, OrderStatus status);

    public OrderDto createOrder(PlaceOrderRequestDto placeOrderRequestDto);

}
