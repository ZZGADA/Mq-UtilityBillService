package com.example.consumer.config;



import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer , AsyncConfigurer {

    // 定义池子的容量
    private static final int CRON_POOL_SIZE = 10;

    // 注册定时任务线程池  配置线程池
    @Bean
    public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        // 初始化线程池
        threadPoolTaskScheduler.initialize();
        // 设置池子容量
        threadPoolTaskScheduler.setPoolSize(CRON_POOL_SIZE);
        return threadPoolTaskScheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 创建线程池，设置五个线程
        taskRegistrar.setScheduler(getThreadPoolTaskScheduler());
    }

    //重写AsyncConfigurer的两个方法
    @Override
    public Executor getAsyncExecutor() {
        return getThreadPoolTaskScheduler();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

}