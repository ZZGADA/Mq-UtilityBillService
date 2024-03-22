package com.example.consumer.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("university_code")
public class UniversityCodePO {
    @TableId(type = IdType.INPUT)
    private String codeId;
    private String universityName;
    private String universityRegion;
}
