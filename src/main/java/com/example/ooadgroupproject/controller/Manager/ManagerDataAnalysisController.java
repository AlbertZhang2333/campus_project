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
import java.util.Arrays;
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
    @GetMapping("/getTheseDaysRoomReservationNum")
    public Result getEveryRoomReservationNum(){
        //得到今天
        Date date=new Date(System.currentTimeMillis());
         //得到两天前
        Date twoDaysAgo=new Date(date.getTime()-2*24*60*60*1000);
        //得到昨天
        Date yesterday=new Date(date.getTime()-24*60*60*1000);
        NumCountObject[]yesterdayObjects=reservationRecordService.findEveryRoomReservationNum(yesterday);
        NumCountObject[]twoDaysAgoObjects=reservationRecordService.findEveryRoomReservationNum(twoDaysAgo);
        NumCountObject[]todayObjects=reservationRecordService.findEveryRoomReservationNum(date);
        for(int i=0;i<todayObjects.length;i++){
            for(int j=0;j<yesterdayObjects.length;j++){
                if(todayObjects[i].content.equals(yesterdayObjects[j].content)){
                    todayObjects[i].num+=yesterdayObjects[j].num;
                }
            }
        }

        for(int i=0;i<todayObjects.length;i++){
            for(int j=0;j<twoDaysAgoObjects.length;j++){
                if(todayObjects[i].content.equals(twoDaysAgoObjects[j].content)){
                    todayObjects[i].num+=twoDaysAgoObjects[j].num;
                }
            }
        }
        Arrays.sort(todayObjects);
        //取前十个
        NumCountObject[]tenObjects=new NumCountObject[10];
        if(todayObjects.length>10){
            for(int i=0;i<10;i++){
                tenObjects[i]=todayObjects[i];
            }
            Arrays.sort(tenObjects);
            return Result.success(tenObjects);
        }
        Arrays.sort(todayObjects);
        return Result.success(todayObjects);

    }
    @GetMapping("/getEveryRoomReservationNum")
    public Result getEveryRoomReservationNum(@RequestParam Date date){
        NumCountObject[]countObjects=reservationRecordService.findEveryRoomReservationNum(date);
        NumCountObject[]tenObjects=new NumCountObject[10];
        if(countObjects.length>10){
            for(int i=0;i<10;i++){
                tenObjects[i]=countObjects[i];
            }
            Arrays.sort(tenObjects);
            return Result.success(tenObjects);
        }
        Arrays.sort(countObjects);
        return Result.success(countObjects);
    }
}
