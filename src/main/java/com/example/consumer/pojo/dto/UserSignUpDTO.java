package com.example.consumer.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDTO {
    @NotNull
    private String mail;
    @NotNull
    private String userName;
    @NotNull
    private String universityCodeId;
    @NotNull
    private Integer dormitoryId;
    @NotNull
    private Integer dormitoryRoomId;
}
