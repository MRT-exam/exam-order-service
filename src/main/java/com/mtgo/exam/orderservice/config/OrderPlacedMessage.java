package com.mtgo.exam.orderservice.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtgo.exam.orderservice.dto.OrderDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderPlacedMessage {
    private int id;
    private int orderId;
    private String orderNumber;
    private String restaurantId;
}
