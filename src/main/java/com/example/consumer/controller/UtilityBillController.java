package com.example.consumer.controller;


import com.example.consumer.pojo.dto.UserSignUpDTO;
import com.example.consumer.pojo.po.UniversityCodePO;
import com.example.consumer.service.impl.UserLoginService;
import com.example.consumer.service.impl.UtilityBillsService;
import com.example.consumer.utils.WebResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/utilityBill")
@RequiredArgsConstructor
public class UtilityBillController {
    private final UtilityBillsService utilityBillsService;
    private final UserLoginService userLoginService;

    /**
     * 针对已经注册的用户，传入用户邮箱，即可查询用户宿舍的水电费情况
     * 并发送邮件信息给用户
     *
     * @param recipient: 用户接收方的邮件
     * @return [java.lang.String]
     */

    @GetMapping("/getBill")
    public WebResponseUtil<Void> getUtilityBill(@RequestParam(required = false) String recipient){
        utilityBillsService.sendBill(recipient);
        return WebResponseUtil.Success();
    }

    /**
     *  查询学校信息数据用于返回学校信息列表 给前端做下拉列表展示
     *
     * @return [] 返回的学校信息列表
     */

    @GetMapping("/getUniversityInformation")
    public WebResponseUtil<List<UniversityCodePO>> getUniversityInformation(){
        return    WebResponseUtil.Success(userLoginService.getUniversityInformation());
    }


    @PostMapping("/userSignUp")
    public WebResponseUtil<Void> SignUp(@Validated  @RequestBody UserSignUpDTO userSignUpDTO){
        System.out.println(userSignUpDTO.toString());
        userLoginService.userSignUp(userSignUpDTO);
        return WebResponseUtil.Success();

    }

}
