package com.mtgo.exam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderLineDto {
    private String itemId;
    private String itemName;
    private BigDecimal price;
    private int quantity;
}
