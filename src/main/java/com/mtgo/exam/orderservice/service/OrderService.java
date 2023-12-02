package com.mtgo.exam.orderservice.service;

import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService implements IOrderService{

    private final IOrderRepository orderRepository;
    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return null;
    }

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        log.info("Order created");
        return null;
    }
}
