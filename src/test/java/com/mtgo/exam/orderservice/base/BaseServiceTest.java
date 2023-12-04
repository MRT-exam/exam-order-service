package com.mtgo.exam.orderservice.base;

import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ComponentScan(basePackages = {"com.mtgo.exam.orderservice.service"})
public abstract class BaseServiceTest extends BaseRepositoryTest{

    protected OrderRequestDto createOrderRequestDto(){
        OrderLineDto orderLineDto = OrderLineDto.builder()
                .itemId("itemtestId")
                .itemName("itemTestName")
                .price(new BigDecimal(200))
                .quantity(2)
                .build();

        List<OrderLineDto> orderLineDtoList = new ArrayList<>();
        orderLineDtoList.add(orderLineDto);

        CustomerInfoDto customerInfoDto = CustomerInfoDto.builder()
                .userId(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .phone(12345678)
                .address("testAddress")
                .build();

        return new OrderRequestDto().builder()
                .restaurantId("restaurant1")
                //.orderDateTime(LocalDateTime.of(2023, 12, 3, 16, 11))
                .orderLineDtoList(orderLineDtoList)
                .comment("testComment")
                .customerInfoDto(customerInfoDto)
                .build();
    }
}
