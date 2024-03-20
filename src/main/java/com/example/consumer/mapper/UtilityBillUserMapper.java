package com.example.consumer.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.consumer.pojo.dto.UtilityBillUserLocationDTO;
import com.example.consumer.pojo.po.UtilityBillUserPO;

import java.util.List;

public interface UtilityBillUserMapper extends BaseMapper<UtilityBillUserPO> {

    default UtilityBillUserLocationDTO getUtilityBillUserExceptMail(String mail){
        LambdaQueryWrapper<UtilityBillUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(UtilityBillUserPO::getUserName,
                UtilityBillUserPO::getDormitoryId,
                UtilityBillUserPO::getDormitoryRoomId,
                UtilityBillUserPO::getUniversityCodeId);
        wrapper.eq(UtilityBillUserPO::getIfDeleted,0);
        wrapper.eq(UtilityBillUserPO::getMail,mail);
        // 返回父类
        return this.selectOne(wrapper);
    }

    /**
     * 获得注册用户的宿舍信息和对应学校的cookie 用于发送http请求查询宿舍水电费
     *
     */

    List<UtilityBillUserLocationDTO> getAllUtilityBillUser();

    List<UtilityBillUserLocationDTO> getDormitoryRoomIdsGroupBy();
}
