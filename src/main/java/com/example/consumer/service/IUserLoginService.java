package com.example.consumer.service;

import com.example.consumer.pojo.dto.UserSignUpDTO;
import com.example.consumer.pojo.po.UniversityCodePO;

import java.util.List;

public interface IUserLoginService {
    public List<UniversityCodePO> getUniversityInformation();

    public void userSignUp(UserSignUpDTO userSignUpDTO);
}
