package com.example.consumer.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Value;
@Getter
@AllArgsConstructor
public enum MailEnum {
    /**
      * MailSubjectTest 邮件主题==>测试
      * MailSendingUserName 邮件发送人
      * MailSendingPhoneNumber 邮件发送人联系方式
      * MailSendingUserQQMail 邮件发送人QQMail联系方式
      *
      */

    MailSubjectTest("张赞发来的测试邮件"),
    MailSendingUserName("ZZGEDA_张赞"),
    MailSendingPhoneNumber("19902900670"),
    MailSendingUserQQMail("1293177585@qq.com");


    private final String content;

}
