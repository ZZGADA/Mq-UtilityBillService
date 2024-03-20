package com.example.consumer.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilityBillUserLocationDTO {
    protected String mail;
    protected String userName;
    protected Integer dormitoryId;
    protected Integer dormitoryRoomId;
    protected String universityCodeId;
    protected Integer ifDeleted;
    protected String cookie;
    private String bill;
}
