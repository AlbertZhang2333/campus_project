package com.example.ooadgroupproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheClient {
    public static final String RESERVATION_RECORD_KEY="reservation_record_key";


    @Autowired
    RedisTemplate<String,Object>redisTemplate;
    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
}
