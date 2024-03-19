package com.example.consumer.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.example.consumer.mapper"})
public class MybatisPlusConfig {

}
