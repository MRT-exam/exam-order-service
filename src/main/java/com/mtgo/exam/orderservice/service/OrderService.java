package com.mtgo.exam.orderservice.service;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.PlaceOrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.exception.error.OrderNotFoundException;
import com.mtgo.exam.orderservice.model.CustomerInfo;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.model.OrderLine;
import com.mtgo.exam.orderservice.repository.IOrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService implements IOrderService{

    private final IOrderRepository orderRepository;

    @Transactional
    @Override
    public List<OrderDto> getOrdersByStatus(String restaurantId, OrderStatus status) {
        List<Order> orders = orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders with status " + status.getCode() + "could be found for restaurant " + restaurantId);
        }
        return orders.stream().map(this::mapOrderToDto).toList();
    }

    @Transactional
    @Override
    public OrderDto updateOrderStatus(int orderId, OrderStatus orderStatus){

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException("Order with id: " + orderId + " could not be found"));

        order.setStatus(orderStatus);
        Order updatedOrder = orderRepository.save(order);
        return this.mapOrderToDto(updatedOrder);
    }

    @Transactional
    public OrderDto getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " could not be found"));
        return mapOrderToDto(order);
    }

    @Transactional
    @Override
    public OrderDto createOrder(PlaceOrderRequestDto placeOrderRequestDto) {
        Order order = new Order();

        List<OrderLine> orderLines = placeOrderRequestDto.getOrderLineDtoList()
                        .stream()
                        .map(this::mapOrderLineFromDto)
                        .toList();

        CustomerInfo customerInfo = this.mapCustomerInfoFromDto(placeOrderRequestDto.getCustomerInfoDto());

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDateTime(placeOrderRequestDto.getOrderDateTime());
        order.setRestaurantId(placeOrderRequestDto.getRestaurantId());
        order.setOrderLines(orderLines);
        order.setTotalPrice(this.calcTotalPrice(orderLines));
        order.setComment(placeOrderRequestDto.getComment());
        order.setCustomerInfo(customerInfo);

        Order savedOrder = orderRepository.save(order);

        return this.mapOrderToDto(savedOrder);
    }

    private BigDecimal calcTotalPrice(List<OrderLine> orderLines) {
        BigDecimal total = new BigDecimal(0);
        for (OrderLine o: orderLines) {
            total = total.add(o.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())));
        }
        return total;
    }

    private OrderLine mapOrderLineFromDto(OrderLineDto orderLineDto) {
        OrderLine orderLine = new OrderLine();
        orderLine.setItemId(orderLineDto.getItemId());
        orderLine.setItemName(orderLineDto.getItemName());
        orderLine.setPrice(orderLineDto.getPrice());
        orderLine.setQuantity(orderLineDto.getQuantity());
        return orderLine;
    }

    private OrderLineDto mapOrderLineToDto(OrderLine orderLine) {
        OrderLineDto orderLineDto = new OrderLineDto();
        orderLineDto.setItemId(orderLine.getItemId());
        orderLineDto.setItemName(orderLine.getItemName());
        orderLineDto.setPrice(orderLine.getPrice());
        orderLineDto.setQuantity(orderLine.getQuantity());
        return orderLineDto;
    }

    private CustomerInfo mapCustomerInfoFromDto(CustomerInfoDto customerInfoDto) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setFirstName(customerInfoDto.getFirstName());
        customerInfo.setLastName(customerInfoDto.getLastName());
        customerInfo.setPhone(customerInfoDto.getPhone());
        customerInfo.setAddress(customerInfoDto.getAddress());
        return customerInfo;
    }

    private CustomerInfoDto mapCustomerInfoToDto(CustomerInfo customerInfo) {
        CustomerInfoDto customerInfoDto = new CustomerInfoDto();
        customerInfoDto.setFirstName(customerInfo.getFirstName());
        customerInfoDto.setLastName(customerInfo.getLastName());
        customerInfoDto.setPhone(customerInfo.getPhone());
        customerInfoDto.setAddress(customerInfo.getAddress());
        return customerInfoDto;
    }

    private OrderDto mapOrderToDto(Order order) {
        OrderDto orderDto = new OrderDto();

        List<OrderLineDto> orderLineDtoList = order.getOrderLines()
                .stream()
                .map(this::mapOrderLineToDto)
                .toList();

        CustomerInfoDto customerInfoDto = this.mapCustomerInfoToDto(order.getCustomerInfo());

        orderDto.setId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderDateTime(order.getOrderDateTime());
        orderDto.setOrderLineDtoList(orderLineDtoList);
        orderDto.setTotalPrice(this.calcTotalPrice(order.getOrderLines()));
        orderDto.setComment(order.getComment());
        orderDto.setCustomerInfoDto(customerInfoDto);
        orderDto.setRestaurantId(order.getRestaurantId());

        return orderDto;
    }

}
