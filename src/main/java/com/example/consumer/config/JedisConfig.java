package com.example.consumer.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
@Slf4j
public class JedisConfig {
    @Value("${spring.redis.jedis.host}")
    private String redisHost;

    @Value("${spring.redis.jedis.port}")
    private Integer redisPort;

    @Value("${spring.redis.jedis.password}")
    private String redisPassword;

    @Value("${spring.redis.jedis.database}")
    private Integer database;

    @Value("${spring.redis.jedis.maxIdle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.minIdle}")
    private Integer minIde;

    @Value("${spring.redis.jedis.maxTotal}")
    private Integer maxTotal;

    @Value("${spring.redis.jedis.maxWaitMillis}")
    private Long maxWaitMillis;

    @Value("${spring.redis.jedis.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${spring.redis.jedis.testOnReturn}")
    private Boolean testOnReturn;


    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIde);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWait(Duration.ofMillis(maxWaitMillis));  //创建毫秒级的数据段
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);

        log.info("redis properties {}, jedisPoolConfig={}" , this, JSONObject.toJSONString(jedisPoolConfig));
        return new JedisPool(jedisPoolConfig, redisHost, redisPort,0, redisPassword, database);
    }

    @Override
    public String toString() {
        return "JedisConfig{" +
                "redisHost='" + redisHost + '\'' +
                ", redisPort=" + redisPort +
                ", redisPassword='" + redisPassword + '\'' +
                ", database=" + database +
                '}';
    }
}
