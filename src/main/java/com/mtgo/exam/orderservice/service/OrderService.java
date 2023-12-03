package com.mtgo.exam.orderservice.service;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.CustomerInfo;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.model.OrderLine;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    public OrderDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        List<OrderLine> orderLines = orderRequestDto.getOrderLineDtoList()
                        .stream()
                        .map(this::mapOrderLineFromDto)
                        .toList();
        CustomerInfo customerInfo = this.mapCustomerInfoFromDto(orderRequestDto.getCustomerInfoDto());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDateTime(orderRequestDto.getOrderDateTime());
        order.setRestaurantId(orderRequestDto.getRestaurantId());
        order.setOrderLines(orderLines);
        order.setTotalPrice(this.calcTotalPrice(orderLines));
        order.setComment(orderRequestDto.getComment());
        order.setCustomerInfo(customerInfo);

        orderRepository.save(order);



        return null;
    }

    private BigDecimal calcTotalPrice(List<OrderLine> orderLines) {
        BigDecimal total = new BigDecimal(0);
        for (OrderLine o: orderLines) {
            total.add(o.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())));
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

    private CustomerInfo mapCustomerInfoFromDto(CustomerInfoDto customerInfoDto) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setFirstName(customerInfoDto.getFirstName());
        customerInfo.setLastName(customerInfoDto.getLastName());
        customerInfo.setPhone(customerInfoDto.getPhone());
        customerInfo.setAddress(customerInfoDto.getAddress());
        return customerInfo;
    }

    private OrderDto mapOrderDtoToEntity(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderDateTime(order.getOrderDateTime());

    }

}
