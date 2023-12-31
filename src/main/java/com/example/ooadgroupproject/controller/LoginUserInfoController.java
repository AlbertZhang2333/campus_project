package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TheLoginUserInfo")
public class LoginUserInfoController {
    @GetMapping("/getLoginUserMail")
    public Result getLoginUserMail(){
        Account account= LoginUserInfo.getAccount();
        if(account.getUserMail()!=null) {
            return Result.success(account.getUserMail());
        }else return Result.fail("用户未登录");
    }
}
