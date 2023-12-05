package com.mtgo.exam.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = OrderServiceApplication.class)
@ComponentScan(basePackages = "com.mtgo.exam.orderservice")
class OrderServiceApplicationTests {

}
