package com.mtgo.exam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDto {
    private String firstName;
    private String lastName;
    private int phone;
    private String address;
}
