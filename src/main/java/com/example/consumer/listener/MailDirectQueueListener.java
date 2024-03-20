package com.example.consumer.listener;

import com.example.consumer.pojo.dto.MailSendingMqDTO;
import com.example.consumer.service.impl.MailSendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(name = "mail.queue"),
                exchange = @Exchange(name = "mail.direct"),
                key = {"mail"}
        )
)
public class MailDirectQueueListener {
    // 这里应该应该会涉及springBoot的三级缓存 解决循环依赖
    private final MailSendingService mailSendingService;


    /**
     * 使用@RabbitHandler注解时，可以在方法的参数中指定消息的类型，Spring AMQP会根据消息的内容类型自动进行转换
     * Payload获取body中的信息 @Header获取请求头的信息
     * 可以根据需求，只要提取自己想要的属性字段就好了
     */
    @RabbitHandler
    public void listenMailObjectDirectMessage(@Payload MailSendingMqDTO mailSendingMqDTO, @Header MessageProperties messageProperties, @Header String __TypeId__) {
        log.info("成功接收到mq的消息，邮件详情消息如下");
        log.info(mailSendingMqDTO.toString());
        log.info(messageProperties.toString());
        log.info(__TypeId__);
        //有了这个type_id 我就通过反射将我的body中的数据转换成目标数据类型
        mailSendingService.sendMailFormQQMail(mailSendingMqDTO.getRecipient(), mailSendingMqDTO.getDormitoryBill());
        log.info("=====>  邮件发送成功");
    }
}
