package com.example.consumer.service.impl;

import com.example.consumer.mapper.UniversityCodeMapper;
import com.example.consumer.mapper.UtilityBillUserMapper;
import com.example.consumer.pojo.dto.UserSignUpDTO;
import com.example.consumer.pojo.po.UniversityCodePO;
import com.example.consumer.service.IUserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginService implements IUserLoginService {
    private final UniversityCodeMapper universityCodeMapper;
    private final UtilityBillUserMapper utilityBillUserMapper;
    private final MailSendingService mailSendingService;


    @Override
    public  List<UniversityCodePO> getUniversityInformation(){
        return universityCodeMapper.selectList(null);
    }

    @Override
    public void userSignUp(UserSignUpDTO userSignUpDTO){
        // Collections.singletonList(userSignUpDTO.getMail()) 创建包含字符串的不可改变的列表
        if(utilityBillUserMapper.selectBatchIds(Collections.singletonList(userSignUpDTO.getMail())).size()==0){
            mailSendingService.sendHtmlMailFormQQMail(userSignUpDTO.getMail(), userSignUpDTO.getUserName(), UUID.randomUUID().toString());
        }
    }
}
