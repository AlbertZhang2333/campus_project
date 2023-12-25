package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ManageDataAnalysis")
public class ManagerDataAnalysisController {
    @Autowired
    AccountService accountService;
    @Autowired
    RoomService roomService;
    @RequestMapping("/usersNumber")
    public int usersNumber(){
        return accountService.findAll().size();
    }
//    @RequestMapping("/roomsNumber")
//    public int roomsNumber(){
//
//    }
}
