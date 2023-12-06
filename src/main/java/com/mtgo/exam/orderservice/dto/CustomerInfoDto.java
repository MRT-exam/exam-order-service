package com.mtgo.exam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerInfoDto implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private int phone;
    private String address;
}
