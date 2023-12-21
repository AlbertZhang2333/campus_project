package com.example.ooadgroupproject.dao;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisConnection {
    RedisTemplate<Integer,Integer>res=new RedisTemplate();
}
