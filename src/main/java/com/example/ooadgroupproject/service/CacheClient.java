package com.example.ooadgroupproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheClient {
    public static final String RESERVATION_RECORD_KEY="reservation_record_key:";


    @Autowired
    RedisTemplate<String,Object>redisTemplate;
    public void set(String key, Object value, long ttl, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,ttl,timeUnit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public Boolean delete(String key){
       return redisTemplate.opsForValue().getOperations().delete(key);
    }
}
