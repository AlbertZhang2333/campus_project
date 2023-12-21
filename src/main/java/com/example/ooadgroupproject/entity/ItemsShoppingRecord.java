package com.example.ooadgroupproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;

@Entity
public class ItemsShoppingRecord {
    @Id
    @GeneratedValue
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
    @Getter
    private Time createTime;
    @NotNull
    @Getter
    private String userMail;
    public ItemsShoppingRecord(Item item, int num,
                               String userMail) {
        this.ItemName = item.getName();
        this.num = num;
        this.amount = this.num * item.getPrice();
        this.isRefund = false;
        this.createTime = Time.valueOf(LocalTime.now());
        this.userMail = userMail;
    }


    public ItemsShoppingRecord() {

    }
}
