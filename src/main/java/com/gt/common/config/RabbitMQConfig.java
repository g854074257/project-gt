package com.gt.common.config;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        // 配置允许列表
        converter.setAllowedListPatterns(List.of("com.gt.*", "java.util.*"));
        return converter;
    }
}
