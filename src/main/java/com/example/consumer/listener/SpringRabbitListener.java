package com.example.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class SpringRabbitListener {
    // 利用RabbitListener来声明要监听的队列信息
    // 将来一旦监听的队列中有了消息，就会推送给当前服务，调用当前方法，处理消息。
    // 可以看到方法体中接收的就是消息体的内容
    @RabbitListener(queues = "sample.queue")
    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");

    }

}
/**
 *
 *这是单一的一个mq的监听器  当时如果消息处理的时候比较耗时 可能当生产消息的速度远远大于消息的消费速度，
 * 那么就会造成消息的堆压 所以我们可以设置多个监听器 监听一个队列 称之为work模型然后 调高消息处理的速度
 */
