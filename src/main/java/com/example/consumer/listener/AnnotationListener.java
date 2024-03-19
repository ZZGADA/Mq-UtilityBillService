package com.example.consumer.listener;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalTime;
import java.util.Map;

//@Component
public class AnnotationListener {
    /**
     *  使用注解的形式是为了简化bean对象的配置 因为对于topic和direct每一个exchange和queue的key都需要单独配对 非常的累
     *  所以直接使用注解配置
     * 基于注解的形式声明DirectExchange和队列 如果queue或者exchange重复就不创建 否则创建
     *  1.如果是使用rabbitMQ里面已经配置的exchange和queue的话 需要保证exchange中key的配置正确 如果错误的话就会报错
     *      1.1. 正常来说 我们会在一个实例中去声明并创建一个queue 然后使用现有的exchange 所以绑定exchange的Key类型很重要
     *      1.2. 队列在声明（declare）后才能被使用。如果一个队列尚不存在，声明一个队列会创建它。如果声明的队列已经存在，
     *      并且属性完全相同，那么此次声明不会对原有队列产生任何影响。如果声明中的属性与已存在队列的属性有差异，那么一个错误代码为 406 的通道级异常就会被抛出。
     * @param msg:
     * @return [java.lang.String]
     */
    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name = "direct.AQueue"),
            exchange=@Exchange(name = "hmall.direct3" ,type = ExchangeTypes.DIRECT),
            key = {"blue","red"}))
    public void ListenDirectQueue1(String msg){
        System.out.println("消费者annotation1接收direct.AQueue1的消息 ["+msg+"]");
    }


    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name = "Object.queue1"),
            exchange = @Exchange(name = "object.direct",type = ExchangeTypes.DIRECT),
            key = {"zzgeda","ZZGEDA"}
    ))

    public void ListenObjectQueue(Map<String,Object> map){
        System.out.println(LocalTime.now());
        map.forEach((k,v)->{
            System.out.println(k);
            System.out.println(v.toString());
            System.out.println("==============");
        });

    }
}
