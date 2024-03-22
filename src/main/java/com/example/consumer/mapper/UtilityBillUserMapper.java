package com.example.consumer.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.consumer.pojo.dto.UtilityBillUserDTOLocationDTO;
import com.example.consumer.pojo.dto.UtilityBillUserDTO;
import com.example.consumer.pojo.po.UtilityBillUserDTOPO;

import java.util.List;

public interface UtilityBillUserMapper extends BaseMapper<UtilityBillUserDTOPO> {

    default UtilityBillUserDTO getUtilityBillUserExceptMail(String mail){
        LambdaQueryWrapper<UtilityBillUserDTOPO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(UtilityBillUserDTOPO::getUserName,
                UtilityBillUserDTOPO::getDormitoryId,
                UtilityBillUserDTOPO::getDormitoryRoomId,
                UtilityBillUserDTOPO::getUniversityCodeId);
        wrapper.eq(UtilityBillUserDTOPO::getIfDeleted,0);
        wrapper.eq(UtilityBillUserDTOPO::getMail,mail);
        // 返回父类
        return this.selectOne(wrapper);
    }

    /**
     * 获得注册用户的宿舍信息和对应学校的cookie 用于发送http请求查询宿舍水电费
     *
     */

    List<UtilityBillUserDTOLocationDTO> getAllUtilityBillUser();

    List<UtilityBillUserDTOLocationDTO> getDormitoryRoomIdsGroupBy();
}
