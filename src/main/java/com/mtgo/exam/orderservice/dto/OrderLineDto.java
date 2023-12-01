package com.mtgo.exam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderLineDto {
    private int id;
    private String itemName;
    private BigDecimal price;
    private int quantity;
}
