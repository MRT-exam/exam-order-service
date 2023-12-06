package com.mtgo.exam.orderservice.message;

import lombok.*;
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
