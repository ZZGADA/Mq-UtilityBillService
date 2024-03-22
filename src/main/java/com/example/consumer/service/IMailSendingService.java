package com.example.consumer.service;

public interface IMailSendingService {
    void sendSimpleMailFormQQMail(String recipient ,String bill);

    void sendHtmlMailFormQQMail(String recipient ,String userName,String uuid);
}
