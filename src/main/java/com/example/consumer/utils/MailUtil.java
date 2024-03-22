package com.example.consumer.utils;

import com.example.consumer.pojo.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class MailUtil {
    @Value("${spring.mail.username}")
    private String sender; //邮件发送者
    @Value("${now.host.ip}")
    private String ip;//连接地址ip

    @Value("${server.port}")
    private String port;

    @Resource
    private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;



    /**
     * 发送文本邮件
     * 一些基础信息就直接静态配置了
     *
     * @param mail：邮件信息
     */
    public void sendSimpleMail(Mail mail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender); //邮件发送者
            mailMessage.setTo(mail.getRecipient()); // 邮件发给的人
            mailMessage.setSubject(mail.getSubject());  // 邮件主题
            mailMessage.setText(mail.getContent());  // 邮件内容
            //mailMessage.copyTo(copyTo);

            javaMailSender.send(mailMessage);
            log.info("邮件发送成功 收件人：{}", mail.getRecipient());
        } catch (Exception e) {
            log.error("邮件发送失败 {}", e.getMessage());
            throw new RuntimeException("邮件发送失败");
        }
    }

    public void sendHtmlMail(Mail mail) {
//        复杂邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        System.out.println(mail);
        try {
            mimeMessage.setFrom(sender);
            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mail.getRecipient()));
            mimeMessage.setSubject(mail.getSubject());
            Context context = new Context();
            context.setVariable("userName",mail.getUserName());
            context.setVariable("href",String.format("http://%s:%s/utilityBill/userSignUp?signUpUUUID=%s",ip,port,mail.getUuid()));
            mimeMessage.setText(templateEngine.process("userSignUp.html", context));
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("html邮件发送失败 {}", e.getMessage());
            throw new RuntimeException("邮件发送失败");
        }
    }
}
