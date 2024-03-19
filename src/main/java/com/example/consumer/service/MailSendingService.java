package com.example.consumer.service;

import com.example.consumer.pojo.Mail;
import com.example.consumer.pojo.entity.MailEnum;
import com.example.consumer.pojo.entity.UtilityBillEnum;
import com.example.consumer.utils.MailContextUtil;
import com.example.consumer.utils.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSendingService {

    private final MailUtil mailUtil;
    private final MailContextUtil mailContextUtil;
    public void sendMailFormQQMail(String recipient ,String bill){
        Mail mail = new Mail();
        // 接收者
        mail.setRecipient(recipient);
        mail.setSubject(MailEnum.MailSubjectTest.getContent());
        mail.setContent(mailContextUtil.getFullMailContextMessage(UtilityBillEnum.class,bill));
        mailUtil.sendSimpleMail(mail);
    }
}
