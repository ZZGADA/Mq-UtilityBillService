package com.example.consumer.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
@Slf4j
public class MailContextUtil {
    private static final String getContentMethod = "getContent";
    private static final String getContext = "Context";

    public String getFullMailContextMessage(Class<?> contextEnum, Object... contextItemArgs) throws RuntimeException {
        try {
            // 获取enum下 Context对象
            Field context = contextEnum.getDeclaredField(getContext);
            context.setAccessible(true);
            // get(Object obj) 返回指定对象obj上此 Field 表示的字段的值  返回Context对象
            Object contextObject = context.get(contextEnum);
            // 获取getContent方法
            Method method = contextEnum.getMethod(getContentMethod);
            // 在contextObject执行这个方法
            Object invoke = method.invoke(contextObject);

            return String.format(String.valueOf(invoke), contextItemArgs);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("邮件文本处理错误");
        }
    }

}
