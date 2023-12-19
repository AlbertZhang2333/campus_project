package com.example.ooadgroupproject.service;

import cn.hutool.json.JSONUtil;
import com.example.ooadgroupproject.entity.ReservationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CacheClient {
    public static final String RESERVATION_RECORD_KEY="reservation_record_key:";
    public static final String ACCOUNT_BLACKLIST_KEY="account_blacklist_key:";

    @Autowired
    RedisTemplate<Object,Object>redisTemplate;

    public static String getReservationRecordKey(ReservationRecord reservationRecord){
        String key=RESERVATION_RECORD_KEY+reservationRecord.getRoomName()+":"+reservationRecord.getDate().toString()
                +":"+reservationRecord.getUserMail()+reservationRecord.getId();
        return key;
    }
    public static String getAccountBlacklistKey(String userMail){
        return ACCOUNT_BLACKLIST_KEY+userMail;
    }
    public void set(String key, Object value, long ttl, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,ttl,timeUnit);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public boolean delete(String key){
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }


    public void setReservationRecord(ReservationRecord reservationRecord,
                                        long ttl,TimeUnit timeUnit){
        String key=CacheClient.getReservationRecordKey(reservationRecord);
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(reservationRecord));
    }
    public boolean deleteReservationRecord(Date date,long id,String userMail){
        String key=RESERVATION_RECORD_KEY+date.toString()+":"+userMail+id;
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }
    public void deleteReservationRecord(ReservationRecord reservationRecord){
        String key=CacheClient.getReservationRecordKey(reservationRecord);
        redisTemplate.opsForValue().getOperations().delete(key);
    }
    public List<ReservationRecord> getReservationRecordList(String roomName,Date date){
        List<ReservationRecord>recordArrayList =new ArrayList<>();
        String key=RESERVATION_RECORD_KEY+roomName+":"+date.toString()+":*";
        Set<Object> keys=redisTemplate.keys(key);
        if (keys != null) {
            for(Object key1:keys){
                String key2=(String) key1;
                String value= (String) redisTemplate.opsForValue().get(key2);
                ReservationRecord reservationRecord= JSONUtil.toBean(value,ReservationRecord.class);
                recordArrayList.add(reservationRecord);
            }
        }
        return recordArrayList;
    }
    public void addAccountIntoBlackList(String userMail){
        String key=CacheClient.getAccountBlacklistKey(userMail);
        redisTemplate.opsForValue().set(key,userMail);
    }
    public boolean deleteAccountFromBlackList(String userMail){
        String key=CacheClient.getAccountBlacklistKey(userMail);
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }
    public boolean isAccountInBlackList(String userMail){
        String key=CacheClient.getAccountBlacklistKey(userMail);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }



}
