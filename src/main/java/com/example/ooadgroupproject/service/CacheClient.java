package com.example.ooadgroupproject.service;

import cn.hutool.json.JSONUtil;
import com.example.ooadgroupproject.entity.ReservationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Service
public class CacheClient {
    public static final String RESERVATION_RECORD_KEY="reservation_record_key:";


    @Autowired
    RedisTemplate<String,Object>redisTemplate;

    public static String getReservationRecordKey(ReservationRecord reservationRecord){
        String key=RESERVATION_RECORD_KEY+reservationRecord.getDate().toString()
                +":"+reservationRecord.getUserMail()+reservationRecord.getId();
        return key;
    }
    public void set(String key, Object value, long ttl, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,ttl,timeUnit);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public Boolean delete(String key){
        return redisTemplate.opsForValue().getOperations().delete(key);
    }


    public void setReservationRecord(ReservationRecord reservationRecord,
                                        long ttl,TimeUnit timeUnit){
        String key=CacheClient.getReservationRecordKey(reservationRecord);
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(reservationRecord));
    }
    public void deleteReservationRecord(Date date,long id,String userMail){
        String key=RESERVATION_RECORD_KEY+date.toString()+":"+userMail+id;
        redisTemplate.opsForValue().getOperations().delete(key);
    }
    public void deleteReservationRecord(ReservationRecord reservationRecord){
        String key=CacheClient.getReservationRecordKey(reservationRecord);
        redisTemplate.opsForValue().getOperations().delete(key);
    }

}
