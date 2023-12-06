package com.mtgo.exam.orderservice.producer;

import com.mtgo.exam.orderservice.config.MQConfig;
import com.mtgo.exam.orderservice.message.OrderPlacedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderPlacedMessageProducer {

    @Autowired
    private RabbitTemplate template;

        public String sendOrderPlacedMessage(OrderPlacedMessage orderPlacedMessage) {
        template.convertAndSend(MQConfig.ORDER_PLACED_EXCHANGE,
                MQConfig.ORDER_PLACED_ROUTING_KEY, orderPlacedMessage);
        return "OrderPlacedMessage Published";
    }
}
