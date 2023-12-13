package com.example.ooadgroupproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String,Object>redisTemplate;

    @Test
    void testString(){
        redisTemplate.opsForValue().set("name","测试");
        Object name=redisTemplate.opsForValue().get("name");
        System.out.println("name = "+name);
    }
}
