package com.example.consumer.config;



import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer , AsyncConfigurer {

    // 定义池子的容量
    private static final int CRON_POOL_SIZE = 10;

    /**
     * 注册定时任务线程池  配置线程池
     * ThreadPoolTaskScheduler 用于创建和管理定时任务的线程池。
     *
     */
    @Bean
    public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        // ThreadPoolTaskExecutor和ThreadPoolTaskScheduler都是ExecutorConfigurationSupport的子类
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        // 初始化线程池
        threadPoolTaskScheduler.initialize();
        // 设置池子容量
        threadPoolTaskScheduler.setPoolSize(CRON_POOL_SIZE);
        return threadPoolTaskScheduler;
    }

    /**
     * 实现SchedulingConfigurer中的方法
     * 配置定时任务或者周期任务的线程池
     * ScheduledTaskRegistrar 提供了一些方法来注册定时任务，设置定时任务的执行器（如线程池），
     * 以及配置定时任务的其他属性。通过使用 ScheduledTaskRegistrar，可以更灵活地管理和控制应用中的定时任务，包括设置定时任务的执行频率、延迟时间等
     *
     * @param taskRegistrar:
     * @return [org.springframework.scheduling.config.ScheduledTaskRegistrar]
     */

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 配置定时任务或者周期任务的线程池，设置五个线程
        // 配置任务管理器
        taskRegistrar.setScheduler(getThreadPoolTaskScheduler());
    }

    /**
     * 实现AsyncConfigurer中的方法
     * 用于返回一个 Executor 对象，该对象用于执行异步方法。
     * 可以通过该方法配置异步方法的执行器，如线程池等。
     *
     */
    @Override
    public Executor getAsyncExecutor() {
        return getThreadPoolTaskScheduler();
    }

    /**
     * 实现AsyncConfigurer中的方法
     * 处理异步任务中未捕获异常的情况。
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

}