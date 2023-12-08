package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.Utils.JwtUtils;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/Tool")

public class TestToolController {
    @Autowired
    //jpa会自动依照规范的命名确定出所需求的简单功能，并直接将之实现和实例化
    private AccountService accountService;//=new AccountServiceImpl();new部分可写可不写

    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/findAll")
    public List<Account> findAll(){
        return accountService.findAll();
    }
    @PostMapping("/deleteAll")
    public void deleteAll(){
        for(int i=0;i<100;i++) {
            accountService.deleteById(i);
        }
    }
}
