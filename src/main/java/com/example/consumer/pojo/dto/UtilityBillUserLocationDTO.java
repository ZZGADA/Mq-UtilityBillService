package com.example.consumer.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.consumer.pojo.po.UtilityBillUserPO;
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
}
