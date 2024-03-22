package com.example.consumer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.consumer.pojo.po.UtilityBillUserDTOPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UtilityBillMapper extends BaseMapper<UtilityBillUserDTOPO> {
    @Select("select cookie from university_cookie where code_id=(select university_code_id from utility_bill_user where mail = #{mail})")
    String getUtilityBillCookieByUniversityCode(@Param(value = "mail") String mail);
}
