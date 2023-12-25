package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/roomsNumber")
    public int roomsNumber(){
        return roomService.findAll().size();
    }
    @RequestMapping("/roomNumberByLocation")
    public int roomNumberByLocation(@RequestParam String location){
        return roomService.findRoomByLocation(location).size();
    }
    //接下来做个近期预定房间的统计，统计每个房间相关的预约数目
}
