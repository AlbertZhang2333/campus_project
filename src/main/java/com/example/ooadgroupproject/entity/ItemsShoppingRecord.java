package com.example.ooadgroupproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
    @Getter
    private Time createTime;
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
        this.userMail = userMail;
        this.status=Initial_State;
    }
    public long generateId(){
        long id=0;
        LocalDateTime now = LocalDateTime.now();
        long nowSecond=now.toEpochSecond(ZoneOffset.UTC);
        long timestamp=nowSecond-1000000L;
        Random random=new Random(System.currentTimeMillis());
        id=timestamp+random.nextLong(80000L);
        return id;

    }



    public ItemsShoppingRecord() {

    }
}
