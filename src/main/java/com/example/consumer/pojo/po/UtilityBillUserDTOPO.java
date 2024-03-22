package com.example.consumer.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.consumer.pojo.dto.UtilityBillUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("utility_bill_user")
public class UtilityBillUserDTOPO extends UtilityBillUserDTO {
    @TableId(value = "mail" ,type = IdType.INPUT)
    private String mail;
}
