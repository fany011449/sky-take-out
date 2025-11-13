package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("開始創建Redis模板對象. . . ");

        RedisTemplate redisTemplate = new RedisTemplate();
        // 設置 Redis連接工廠 對象
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 設置 Redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 設定 Redis value 的序列化方式（建議用 JSON）
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
