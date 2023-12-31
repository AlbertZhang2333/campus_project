package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;

import java.sql.Date;

public interface EmailService {
    Result sendVerifyCodeEmail(String userMail);
    boolean verifyEmailCode(String userMail, String code);

    Result sendReservationCanceledEmail(String userMail, String roomName, Date date);
}
