package com.example.consumer.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilityBillDTO {
    private String areaId;
    private Integer buildingCode;
    private Integer floorCode;
    private Integer roomCode;
    private String ymId ;
    private String platform;
}
