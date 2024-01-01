package com.example.ooadgroupproject.service;

import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONObject;
import com.example.ooadgroupproject.common.CartForm;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CacheClient {
    public static final String RESERVATION_RECORD_KEY="reservation_record_key:";
    public static final String ACCOUNT_BLACKLIST_KEY="account_blacklist_key:";

    public static final String ItemsShoppingCart="ItemsShoppingCart:";
    public static final String Item_Info_Key="Item_Info_Key:";
    public static final String ForgetPasswordVerifyCode_Key="forgetPasswordVerifyCode_Key:";
    public static final String Item_shopping_record_key="Item_shopping_record_key:";
    public static final String Instant_Item_key="Item_shopping_instant_Item:";

    @Autowired
    RedisTemplate<Object,Object>redisTemplate;

    public static String getReservationRecordKey(ReservationRecord reservationRecord){
        String key=RESERVATION_RECORD_KEY+reservationRecord.getRoomName()+":"+reservationRecord.getDate().toString()
                +":"+reservationRecord.getUserMail()+reservationRecord.getId();
        return key;
    }
    public static String getReservationRecordKey(String roomName,Date date,String userMail,long id){
        //输出date为"yyyy-MM-dd"格式
        String dateStr=date.toString();
        return RESERVATION_RECORD_KEY+roomName+":"+dateStr+":"+userMail+String.valueOf(id);
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
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(reservationRecord),ttl,timeUnit);
    }

//    public String addItemShoppingRecord(ItemsShoppingRecord itemsShoppingRecord){
//        String key=getItemShoppingRecordKey(itemsShoppingRecord.getId());
//        Random random=new Random();
//        long r=random.nextLong(5000);
//        redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(itemsShoppingRecord),15*60*1000L+r,TimeUnit.MILLISECONDS);
//        return key;
//    }
//    public boolean deleteReservationRecord(Date date,long id,String userMail){
//        String key=RESERVATION_RECORD_KEY+date.toString()+":"+userMail+id;
//        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
//    }

    public boolean deleteReservationRecord(String roomName,Date date,long id,String userMail){
        String key=getReservationRecordKey(roomName,date,userMail,id);
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }

    //TODO
    //需要考虑过期时间
    public boolean cancelReservationRecord(String roomName,Date date,long id,String userMail){
        String key=getReservationRecordKey(roomName,date,userMail,id);
        ReservationRecord reservationRecord=JSONUtil.toBean((String) redisTemplate.opsForValue().get(key),ReservationRecord.class);
        if(reservationRecord!=null){
            reservationRecord.setState(ReservationState.Canceled);
            redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(reservationRecord),1, TimeUnit.DAYS);
            return true;
        }
        return false;
    }
//    public void deleteReservationRecord(ReservationRecord reservationRecord){
//        String key=CacheClient.getReservationRecordKey(reservationRecord);
//        redisTemplate.opsForValue().getOperations().delete(key);
//    }
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
        redisTemplate.opsForValue().set(key,userMail,1000000000L,TimeUnit.DAYS);
    }
    public boolean deleteAccountFromBlackList(String userMail){
        String key=CacheClient.getAccountBlacklistKey(userMail);
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }
    public boolean isAccountInBlackList(String userMail){
        String key=CacheClient.getAccountBlacklistKey(userMail);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


    public void setItemsShoppingCart(String userMail, CartForm cartForm){
        String key=ItemsShoppingCart+userMail+":"+cartForm.getTime();
        redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(cartForm),1L,TimeUnit.DAYS);
    }
    public boolean deleteItemsShoppingCart(String userMail,long cartFormTime){
        String key=ItemsShoppingCart+userMail+":"+ cartFormTime;
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }
    public List<CartForm> getCartForms(String userMail){
        List<CartForm> cartForms=new ArrayList<>();
        String key=ItemsShoppingCart+userMail+":*";
        Set<Object> keys=redisTemplate.keys(key);
        if (keys!=null&&keys.size()>0){
            for(Object key1:keys){
                String key2=(String) key1;
                String value= (String) redisTemplate.opsForValue().get(key2);
                JSONObject jsonObject= JSONUtil.parseObj(value);
                CartForm cartForm= new CartForm(jsonObject);
                cartForms.add(cartForm);
            }
        }
        return cartForms;
    }

    public void setItemInfo(String itemName,Item item){
        String key=Item_Info_Key+itemName;
        Random random=new Random();
        if(item!=null) {
            long t = random.nextLong(5L);
            redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(item), 30L + t, TimeUnit.MINUTES);
        }else {
            redisTemplate.opsForValue().set(key,"",60000L+random.nextLong(20000L),
                    TimeUnit.MILLISECONDS);
        }
    }
    public boolean deleteItems(Item item){
        String key=Item_Info_Key+item.getName();
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }
    public String getItemInfo(String name){
        String key=Item_Info_Key+name;
        String value= (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    public String getVerifyCode_Key(String userMail, String aim){
        return ForgetPasswordVerifyCode_Key+aim+":"+userMail;
    }

    public String addVerifyCode(String userMail,String aim, String verifyCode){
        String key= getVerifyCode_Key(userMail,aim);
        redisTemplate.opsForValue().set(key,verifyCode,10L,TimeUnit.MINUTES);
        return key;
    }
    public boolean checkVerifyCode(String userMail,String aim,String verifyCode){
        String key=getVerifyCode_Key(userMail,aim);
        String value= (String) redisTemplate.opsForValue().get(key);
        if(value==null){
            return false;
        }
        return value.equals(verifyCode);
    }

    public String getItemShoppingRecordKey(long item_shoppingId){
        return Item_shopping_record_key+item_shoppingId;
    }
    public String addItemShoppingRecord(ItemsShoppingRecord itemsShoppingRecord){
        String key=getItemShoppingRecordKey(itemsShoppingRecord.getId());
        Random random=new Random();
        long r=random.nextLong(5000);
        redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(itemsShoppingRecord),15*60*1000L+r,TimeUnit.MILLISECONDS);
        return key;
    }
    public String getItemShoppingRecord(long id) throws JsonProcessingException {
        String key=getItemShoppingRecordKey(id);
        String value= (String) redisTemplate.opsForValue().get(key);
        long random=new Random().nextLong(10000);
        if(value==null){
            //创建null保护信息:
            redisTemplate.opsForValue().set(key,"",60000L+random,TimeUnit.MILLISECONDS);
        }

        return value;
    }
    public String getInstant_Item_key(Item item){
        return Instant_Item_key+item.getName();
    }
    public String getInstant_Item_key(String itemName){
        return Instant_Item_key+itemName;
    }
    public String setInstantItem(Item item, long ttl, TimeUnit timeUnit){
        String key=getInstant_Item_key(item);
        redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(item),ttl,timeUnit);
        return key;
    }
    public String setInstantItem(Item item){
        String key=getInstant_Item_key(item.getName());
        Long ttl=redisTemplate.getExpire(key,TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(item),ttl,TimeUnit.MILLISECONDS);
        return key;
    }
    public List<String> getInstantItem()throws JsonProcessingException{
        String key=String.valueOf(Instant_Item_key.toCharArray(),0,Instant_Item_key.length()-1);
        key=key+"*";
        Set<Object> keys=redisTemplate.keys(key);
        ArrayList<String>res=new ArrayList<>();
        if (keys==null||keys.size()==0){
            return res;
        }
        for(Object key1:keys){
            String key2=(String) key1;
            String value= (String) redisTemplate.opsForValue().get(key2);
            res.add(value);
        }
        return res;
    }
    public String getInstantItem(String itemName){
        String key=getInstant_Item_key(itemName);
        return (String) redisTemplate.opsForValue().get(key);
    }
    public boolean deleteInstantItem(String itemName){
        String key=getInstant_Item_key(itemName);
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().delete(key));
    }















}
