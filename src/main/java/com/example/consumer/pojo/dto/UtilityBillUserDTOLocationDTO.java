package com.example.consumer.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilityBillUserDTOLocationDTO extends UtilityBillUserDTO {
    private String cookie;
    private String bill;
}
