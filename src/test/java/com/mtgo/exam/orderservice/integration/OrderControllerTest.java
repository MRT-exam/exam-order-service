package com.mtgo.exam.orderservice.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderControllerTest {

    @Test
    public void placeOrder() {
        Assertions.assertThat(1).isEqualTo(1);
    }
}
