package com.example.consumer;

import com.example.consumer.pojo.Mail;

import com.example.consumer.service.impl.UtilityBillsService;
import com.example.consumer.utils.MailUtil;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
@Slf4j
//@RequiredArgsConstructor
class ConsumerApplicationTests {

    @Resource //依赖注入 与@Autowired一致 @Resource默认按byName自动注入
    private MailUtil mailUtil;
//    @Autowired
//    private HttpUtil httpUtil;
//    @Autowired
//    private MailContextUtil mailContextUtil;
    @Autowired
    private UtilityBillsService utilityBillsService;
//    private static final String cookie = "shiroJID=341b57d5-d882-4c9b-acc7-443f81d0b6a4";

    //接收人
    private static final String recipient = "leon.bwchen@foxmail.com";

//    @Test
    void MailSending(String bills) {
        Mail mail = new Mail();
//        int code = (int) ((Math.random() * 9 + 1) * 100000);
        mail.setRecipient(recipient);
        mail.setSubject("张赞发来的测试邮件");
        mail.setContent("亲爱的用户：您好！\n" +
                "\n" +
                "\t这是一封来自ZZGEDA_张赞的邮件，请不用理会这封电子邮件。\n" +
                "\t但是：您房间电费剩余 " + bills + "度，如果电力不足请及时充电\n" +
                "\t咨询人：ZZGEDA_张赞 联系电话：19902900670\n"
        );
        mailUtil.sendSimpleMail(mail);
    }

//    @Test
    void queryAllUserSendMessage(){
        utilityBillsService.queryAllUserSendMessage();
    }


    @Test
    void scheduleTask(){
        utilityBillsService.scheduleTask();
    }
}



