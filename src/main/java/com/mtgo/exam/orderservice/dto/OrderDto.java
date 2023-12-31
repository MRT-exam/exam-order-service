package com.mtgo.exam.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDto implements Serializable {
    private int id;
    private String orderNumber;
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd-HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime orderDateTime;
    private String restaurantId;
    private List<OrderLineDto> orderLineDtoList;
    private BigDecimal totalPrice;
    private String comment;
    private CustomerInfoDto customerInfoDto;
}
