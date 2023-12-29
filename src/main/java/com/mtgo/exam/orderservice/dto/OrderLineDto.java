package com.mtgo.exam.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String itemId;
    @NotNull
    @NotBlank
    private String itemName;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;
}
