package com.example.consumer.utils;



import lombok.Data;

import java.util.HashMap;

@Data
public class WebResponseUtil<T> {


        private int code;
        private String msg;
        private T data;

        public static final Integer CodeSuccess=0;
        public static final String MsgSuccess="success";

        public static final Integer CodeError=500;  //这个是报错
        public static final Integer CodeFailed=100;  //逻辑执行结果返回失败 但不是报错  101是报错

        public static WebResponseUtil<Void> Success() {
            return new WebResponseUtil<>(WebResponseUtil.CodeSuccess, MsgSuccess, null);
        }

        public static <T> WebResponseUtil<T> Success(T data, String msg){
            return new WebResponseUtil<>(WebResponseUtil.CodeSuccess,msg,data);
        }

        public static <T>WebResponseUtil<T> Success(Integer code,T data,String msg){
            return new WebResponseUtil<>(code,msg,data);
        }
        public static <T> WebResponseUtil<T> Success(T data){
            return new WebResponseUtil<>(WebResponseUtil.CodeSuccess,WebResponseUtil.MsgSuccess,data);
        }
        public static WebResponseUtil Failed(String msg){
            return new WebResponseUtil<>(WebResponseUtil.CodeFailed,msg, new HashMap());
        }
        public static <T> WebResponseUtil<T> Failed(T data,String msg){
            return new WebResponseUtil<>(WebResponseUtil.CodeFailed,msg, data);
        }

        public static <T> WebResponseUtil<T> error(String msg) {
            return new WebResponseUtil<>(WebResponseUtil.CodeError, msg, null);
        }

        public static <T> WebResponseUtil<T> error(int code, String msg) {
            return new WebResponseUtil<>(code, msg, null);
        }

        public WebResponseUtil(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public boolean success(){
            return code == CodeSuccess;
        }


}
