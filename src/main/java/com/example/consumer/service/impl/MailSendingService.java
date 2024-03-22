package com.example.consumer.service.impl;

import com.example.consumer.pojo.Mail;
import com.example.consumer.pojo.entity.MailEnum;
import com.example.consumer.pojo.entity.UtilityBillEnum;
import com.example.consumer.service.IMailSendingService;
import com.example.consumer.utils.MailContextUtil;
import com.example.consumer.utils.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSendingService implements IMailSendingService {

    private final MailUtil mailUtil;
    private final MailContextUtil mailContextUtil;

    @Override
    public void sendSimpleMailFormQQMail(String recipient ,String bill){
        Mail mail = new Mail();
        // 接收者邮箱
        mail.setRecipient(recipient);
        mail.setSubject(MailEnum.MailSubjectTest.getContent());
        mail.setContent(mailContextUtil.getFullMailContextMessage(UtilityBillEnum.class,bill));
        mailUtil.sendSimpleMail(mail);
    }

    @Override
    public void sendHtmlMailFormQQMail(String recipient, String userName, String uuid) {
        Mail mail = new Mail();
        // 接收者邮箱
        mail.setRecipient(recipient);
        mail.setSubject(MailEnum.MailSubjectTest.getContent());
        mail.setUserName(userName);
        mail.setUuid(uuid);
        mailUtil.sendHtmlMail(mail);
    }
}
