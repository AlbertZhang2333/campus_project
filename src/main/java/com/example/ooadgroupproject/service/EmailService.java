package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;

public interface EmailService {
    Result sendEmail(String userMail);
    boolean verifyCode(String code);
}
