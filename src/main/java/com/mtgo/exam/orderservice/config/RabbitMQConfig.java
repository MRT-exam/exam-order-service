//package com.mtgo.exam.orderservice.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@RequiredArgsConstructor
//@Slf4j
//@Configuration
//public class RabbitMQConfig {
//
//    private final CachingConnectionFactory cachingConnectionFactory;
//
//
//    @Bean
//    public Queue createOrderSubmissionQueue() {
//        return new Queue("q.order-submission");
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
//        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
//        template.setMessageConverter(converter);
//        return template;
//    }
//}
