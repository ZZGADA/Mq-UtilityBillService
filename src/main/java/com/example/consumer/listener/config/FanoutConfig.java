package com.example.consumer.listener.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    /**
     *
     * 声明交换机
     *
     * @param :
     * @return []
     */

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("hmall.fanout");
    }

    /**
     *
     * 第一个队列
     *
     * @param :
     * @return []
     */

    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    /**
     *
     * 无需在rabbitMq 控制台上手动创建 可以直接自动创建
     *
     * @param fanoutQueue1:  自己创建的队列1
     * @param fanoutExchange: 自己创建的exchange2
     * @return [org.springframework.amqp.core.Queue, org.springframework.amqp.core.FanoutExchange]
     */
    @Bean
    public Binding bindingQueue1(Queue fanoutQueue1,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

}
