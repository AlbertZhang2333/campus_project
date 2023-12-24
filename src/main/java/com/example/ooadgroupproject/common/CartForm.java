package com.example.ooadgroupproject.common;

import lombok.Getter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class CartForm {
    @Getter
    private final String itemName;
    @Getter
    private final int num;
    @Getter
    private final String userMail;
    @Getter
    private final long time;
    public CartForm(String itemName, int num, String userMail) {
        this.itemName = itemName;
        this.num = num;
        this.userMail = userMail;
        LocalDateTime now = LocalDateTime.now();
        long nowSecond=now.toEpochSecond(ZoneOffset.UTC);
        this.time=nowSecond;
    }
}
