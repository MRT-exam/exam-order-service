package com.mtgo.exam.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private int userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotEmpty
    private int phone;
    @NotBlank
    private String address;
}
