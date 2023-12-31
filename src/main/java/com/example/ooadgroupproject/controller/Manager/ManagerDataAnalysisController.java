package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.NumCountObject;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.Room;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.ReservationRecordService;
import com.example.ooadgroupproject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/ManageDataAnalysis")
public class ManagerDataAnalysisController {
    @Autowired
    AccountService accountService;
    @Autowired
    RoomService roomService;

    @Autowired
    ReservationRecordService reservationRecordService;

    @GetMapping("/usersNumber")
    public Result usersNumber(){
        return Result.success(accountService.findAll().size());
    }

    @GetMapping("/roomsNumber")
    public Result roomsNumber(){
        return Result.success(roomService.findAll().size());
    }
    @GetMapping("/roomNumberByLocation")
    public Result roomNumberByLocation(@RequestParam String location){
        return Result.success(roomService.findRoomByLocation(location).size());
    }
    //接下来做个近期预定房间的统计，统计每个房间相关的预约数目
    @GetMapping("/getOneReservationNum")
    public Result reservationNum(@RequestParam Date date){
        return Result.success(reservationRecordService.findRecordsByDate(date).size());
    }
    @GetMapping("/getEveryRoomReservationNum")
    public Result getEveryRoomReservationNum(@RequestParam Date date){
        NumCountObject[]countObjects=reservationRecordService.findEveryRoomReservationNum(date);
        return Result.success(countObjects);
    }
}
