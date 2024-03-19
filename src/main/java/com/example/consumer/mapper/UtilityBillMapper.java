package com.example.consumer.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.consumer.pojo.po.UtilityBillUserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UtilityBillMapper extends BaseMapper<UtilityBillUserPO> {
    @Select("select cookie from university_cookie where code_id=(select university_code_id from utility_bill_user where mail = #{mail})")
    String getUtilityBillCookieByUniversityCode(@Param(value = "mail") String mail);
}
