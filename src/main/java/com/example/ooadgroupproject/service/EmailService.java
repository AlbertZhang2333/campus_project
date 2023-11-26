package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Account;

public interface EmailService {
    String sendEmail(String userMail);
    boolean verifyCode(String code);
}
