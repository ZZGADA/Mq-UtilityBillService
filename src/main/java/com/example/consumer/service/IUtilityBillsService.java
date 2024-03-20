package com.example.consumer.service;

import com.example.consumer.pojo.dto.UtilityBillDTO;

public interface IUtilityBillsService {
    String getUtilityBill(String userRecipient);
    String getUtilityBill(UtilityBillDTO utilityBillDTO);
    void sendBill(String recipient);
    void queryAllUserSendMessage();
}
