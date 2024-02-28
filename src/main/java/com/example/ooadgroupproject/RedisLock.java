package com.example.ooadgroupproject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class RedisLock {
    private String name;
    private String usedPlace;
    private RedisTemplate RedisTemplate;
    public RedisLock(String name,RedisTemplate RedisTemplate){
        this.name=name;
        this.RedisTemplate=RedisTemplate;
    }
    private static final String KEY_PREFIX="lock:";
    private static final String ID_PREFIX= UUID.randomUUID().toString()+"-";
    private static final DefaultRedisScript<Long>UNLOCK_SCRIPT;
    static{
        UNLOCK_SCRIPT=new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }
    public RedisLock(String usedPlace){
        this.usedPlace=usedPlace+":";
    }
    public void unlock(){
        RedisTemplate.execute(
                UNLOCK_SCRIPT,
                (List<String>) Collections.singleton(KEY_PREFIX+usedPlace+name),
                ID_PREFIX+usedPlace +Thread.currentThread().getId()
        );
    }
    public boolean tryLock(long timeoutSec){
        String threadId=ID_PREFIX+Thread.currentThread().getId();
        Boolean success= RedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX
                +usedPlace+name,threadId,timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

}
