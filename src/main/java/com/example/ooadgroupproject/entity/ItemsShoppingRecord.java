package com.example.ooadgroupproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private String ItemName;
    @NotNull
    @Getter
    private int num;
    @NotNull
    @Getter
    private double amount;
    @NotNull
    @Getter
    @Setter
    private boolean isRefund;
    @NotNull
    @Getter
    @Setter
    private boolean isPurchased;
    @Getter
    private Time createTime;
    @NotNull
    @Getter
    private String userMail;
    public ItemsShoppingRecord(Item item, int num,
                               String userMail) {
        this.id=generateId();
        this.ItemName = item.getName();
        this.num = num;
        this.amount = this.num * item.getPrice();
        this.isRefund = false;
        this.createTime = Time.valueOf(LocalTime.now());
        this.userMail = userMail;
        this.isPurchased=false;
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
