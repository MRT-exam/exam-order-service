package com.mtgo.exam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderLineDto implements Serializable {
    private String itemId;
    private String itemName;
    private BigDecimal price;
    private int quantity;
}
