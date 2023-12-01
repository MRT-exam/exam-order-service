package com.mtgo.exam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {
    private String restaurantId;
    private List<OrderLineDto> orderLineDtoList;
    private String comment;
    private UserInfoDto userInfoDto;
}
