package com.mtgo.exam.orderservice.utils;

import com.mtgo.exam.orderservice.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String code) {
        if (code == null){
            return null;
        }
        return Stream.of(OrderStatus.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
