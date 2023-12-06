package com.mtgo.exam.orderservice.config;

import com.mtgo.exam.orderservice.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
//@RestController
@Service
public class MessagePublisher {

    @Autowired
    private RabbitTemplate template;

//    @PostMapping("/publish")
//    public String publishMessage(@RequestBody CustomMessage message) {
//        message.setMessageId(UUID.randomUUID().toString());
//        message.setMessageDate(new Date());
//        template.convertAndSend(MQConfig.EXCHANGE,
//                MQConfig.ROUTING_KEY, message);
//
//        return "Message Published";
//    }
        public String publishMessage(OrderPlacedMessage orderPlacedMessage) {
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, orderPlacedMessage);

        return "Message Published";
    }
}
