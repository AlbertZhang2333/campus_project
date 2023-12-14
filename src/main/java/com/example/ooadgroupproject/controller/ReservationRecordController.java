package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import com.example.ooadgroupproject.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Reservation")
public class ReservationRecordController {
    @Autowired
    private ReservationRecordService reservationRecordService;

    private final int PAGE_SIZE = 5;

    @PostMapping("/reservationAdd")
    public Result addOne(@RequestParam String roomName,
                                    @RequestParam Time startTime,
                                    @RequestParam Time endTime,
                                    @RequestParam Date date,
                                    @RequestParam String location){
        Account account= LoginUserInfo.getAccount();
        String userMail=account.getUserMail();
        String username=account.getUsername();
        ReservationRecord reservationRecord=new ReservationRecord(username,userMail,roomName,
                startTime,endTime, date,location);

        return reservationRecordService.validateReservationRecord(reservationRecord);
    }


    @PutMapping("/reservationUpdate")
    public ReservationRecord update(@RequestParam long id,
                                    @RequestParam String userName,
                                    @RequestParam String userMail,
                                    @RequestParam String roomName,
                                    @RequestParam Time startTime,
                                    @RequestParam Time endTime,
                                    @RequestParam Date date,
                                    @RequestParam String location,
                                    @RequestParam int state) {
        ReservationRecord reservationRecord = new ReservationRecord();
        reservationRecord.setId(id);
        reservationRecord.setUserName(userName);
        reservationRecord.setUserMail(userMail);
        reservationRecord.setRoomName(roomName);
        reservationRecord.setStartTime(startTime);
        reservationRecord.setEndTime(endTime);
        reservationRecord.setDate(date);
        reservationRecord.setLocation(location);
        reservationRecord.setState(ReservationState.getByCode(state));
        return reservationRecordService.save(reservationRecord);
    }
    @GetMapping("/UserCheckSelfHistoryReservation")
    public List<ReservationRecord>selfHistoryReservation(){
        return
                reservationRecordService.findRecordsByUserMail
                        (SecurityContextHolder.getContext().getAuthentication().getName());
    }
    @GetMapping("/UserCheckDateSelfReservation")
    public List<ReservationRecord>selfDateReservation(@RequestParam Date date){
        List<ReservationRecord>list=reservationRecordService.findRecordsByUserMail
                (SecurityContextHolder.getContext().getAuthentication().getName());
        List<ReservationRecord>resultList=new ArrayList<>();
        for (ReservationRecord reservationRecord : list) {
            int check = reservationRecord.getDate().compareTo(date);
            if (check == 0) {
                resultList.add(reservationRecord);
            }
        }
        return resultList;
    }


    // 查询某个具体用户的预约记录
    @GetMapping("/reservationRecordsByUserMail")
    public Result getRecordsByUserMail(@RequestParam String userMail) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByUserMail(userMail);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }


    // 查询某天的全部预约记录
    //加一个缓存，这里能提高下性能
    @GetMapping("/reservationRecordsByDate")
    public Result getRecordsByDate(@RequestParam Date date) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByDate(date);
        Long tot = (long) list.size();

        return Result.success(tot,SplitPage.splitList(list, PAGE_SIZE));
    }

    // 查询某栋建筑所有的预约记录
    @GetMapping("/reservationRecordsByLocation")
    public Result getRecordsByLocation(@RequestParam String location) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByLocation(location);
        Long tot = (long) list.size();
        return Result.success(tot,SplitPage.splitList(list, PAGE_SIZE));
    }

    // 查询某个场地的所有预约记录
    @GetMapping("/reservationRecordsByRoomName")
    public Result getRecordsByRoomName(@RequestParam String roomName) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByRoomName(roomName);
        Long tot = (long) list.size();
        return Result.success(tot,SplitPage.splitList(list, PAGE_SIZE));
    }
}
