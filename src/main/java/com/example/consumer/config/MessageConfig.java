package com.example.consumer.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

public class MessageConfig {

    /**
     * 消息转换 不适用jdk自带的序列化
     *
     **/
    @Bean
    public MessageConverter messageConvert(){
        //定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter=new Jackson2JsonMessageConverter();
        //配置自动创建消息id,用于识别不同消息，也可以在业务中基于ID判断幂等性 是否重复数据
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        return jackson2JsonMessageConverter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
