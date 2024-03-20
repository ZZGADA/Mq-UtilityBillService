package com.example.consumer.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum PostRequestEnum {
    ContentTypeXWWWForm("application/x-www-form-urlencoded;charset=UTF-8"),
    Content_Type("Content-Type"),
    Cookie("Cookie");
    private final String value;

}
