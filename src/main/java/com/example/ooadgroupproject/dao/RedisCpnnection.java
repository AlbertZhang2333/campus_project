package com.example.ooadgroupproject.dao;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisCpnnection {
    RedisTemplate<Integer,Integer>res=new RedisTemplate();
}
