package com.example.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {


    @Bean
    public MessageConverter messageConvert(){
        //定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter=new Jackson2JsonMessageConverter();
        //配置自动创建消息id,用于识别不同消息，也可以在业务中基于ID判断幂等性 是否重复数据
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        return jackson2JsonMessageConverter;
    }

    /**
     *
     * 声明交换机
     *
     * @return []
     */

//    @Bean
//    public FanoutExchange fanoutExchange(){
//        return new FanoutExchange("hmall.fanout");
//    }

//    @Bean
//    public Queue directQueue(){
//        return new Queue("direct.queue1");
//    }

    /**
     *
     * 第一个队列
     *
     * @return []
     */

//    @Bean
//    public Queue fanoutQueue1(){
//        return new Queue("fanoutConfig.queue1");
//    }

    /**
     *
     * 无需在rabbitMq 控制台上手动创建 可以直接自动创建
     *
     * @param fanoutQueue1:  自己创建的队列1
     * @param fanoutExchange: 自己创建的exchange2
     * @return [org.springframework.amqp.core.Queue, org.springframework.amqp.core.FanoutExchange]
     */
//    @Bean
//    public Binding bindingQueue1(Queue fanoutQueue1,FanoutExchange fanoutExchange){
//        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
//    }

}
