package com.example.ooadgroupproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Random;

@Entity
public class ItemsShoppingRecord {
    @Id
    @Getter
    private long id;
    @NotNull
    @Getter
    private String itemName;
    @NotNull
    @Getter
    private Integer num;
    @NotNull
    @Getter
    private Double amount;
    @NotNull
    @Getter
    @Setter
    private Integer status;
    @NotNull
    @Getter
    private Time createTime;
    @NotNull
    @Getter
    private Date date;
    @NotNull
    @Getter
    private String userMail;

    @Transient
    public static Integer Initial_State=0;
    @Transient
    public static Integer Purchased_State=1;
    @Transient
    public static Integer Refund_State=2;

    public ItemsShoppingRecord(Item item, Integer num,
                               String userMail) {
        this.id=generateId();
        this.itemName = item.getName();
        this.num = num;
        this.amount = this.num * item.getPrice();
        this.createTime = Time.valueOf(LocalTime.now());
        this.date = Date.valueOf(LocalDate.now());
        this.userMail = userMail;
        this.status=Initial_State;
    }
    public long generateId(){
        long id;
        LocalDateTime now = LocalDateTime.now();
        long nowSecond=now.toEpochSecond(ZoneOffset.UTC)*1000000L;
        long timestamp=nowSecond-10L;
        Random random=new Random(System.currentTimeMillis());
        id=timestamp+random.nextLong(999999L);
        return id;
    }

    public ItemsShoppingRecord() {

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ItemsShoppingRecord){
            ItemsShoppingRecord itemsShoppingRecord=(ItemsShoppingRecord)obj;
            return this.id == itemsShoppingRecord.getId()
                    && Objects.equals(this.amount, itemsShoppingRecord.getAmount())
                    && this.itemName.equals(itemsShoppingRecord.getItemName())
                    && Objects.equals(this.num, itemsShoppingRecord.getNum());
        }return false;
    }
    public boolean ifEquals(long id,Double amount){
        return this.id == id
                && Objects.equals(this.amount, amount);
    }
    public static ItemsShoppingRecord getItemShoppingRecordFromJson(String json){
        ItemsShoppingRecord itemsShoppingRecord=new ItemsShoppingRecord();
        JSONObject jsonObject=new JSONObject(json);
        itemsShoppingRecord.id=jsonObject.getLong("id");
        itemsShoppingRecord.itemName=jsonObject.getStr("itemName");
        itemsShoppingRecord.num=jsonObject.getInt("num");
        itemsShoppingRecord.amount=jsonObject.getDouble("amount");
        itemsShoppingRecord.status=jsonObject.getInt("status");
        itemsShoppingRecord.createTime= (Time) jsonObject.get("createTime");
        itemsShoppingRecord.userMail=jsonObject.getStr("userMail");
        itemsShoppingRecord.date= (Date) jsonObject.get("date");
        return itemsShoppingRecord;
    }
}
