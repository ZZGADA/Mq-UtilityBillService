package com.example.consumer.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.consumer.pojo.dto.UtilityBillUserLocationDTO;
import com.example.consumer.pojo.po.UtilityBillUserPO;

public interface UtilityBillUserMapper extends BaseMapper<UtilityBillUserPO> {

    default UtilityBillUserLocationDTO getUtilityBillUserExceptMail(String mail){
        LambdaQueryWrapper<UtilityBillUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(UtilityBillUserPO::getUserName,
                UtilityBillUserPO::getDormitoryId,
                UtilityBillUserPO::getDormitoryRoomId,
                UtilityBillUserPO::getUniversityCodeId);
        wrapper.eq(UtilityBillUserPO::getMail,mail);
        // 返回父类
        return this.selectOne(wrapper);
    }
}
