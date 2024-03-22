package com.example.consumer.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilityBillUserDTO {
    private String mail;
    private String userName;
    private Integer dormitoryId;
    private Integer dormitoryRoomId;
    private String universityCodeId;
    private Integer ifDeleted;
}
