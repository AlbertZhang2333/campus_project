package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import com.example.ooadgroupproject.service.ReservationRecordService;
import com.example.ooadgroupproject.service.RoomService;
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
    private final int PAGE_SIZE = 5;
    @Autowired
    private ReservationRecordService reservationRecordService;
    @Autowired
    private RoomService roomService;

    //用户创建预约
    // TODO
    @PostMapping("/reservationAdd")
    public Result addOne(@RequestParam String roomName,
                                    @RequestParam Time startTime,
                                    @RequestParam Time endTime,
                                    @RequestParam Date date,
                                    @RequestParam String location){
        Account account= LoginUserInfo.getAccount();
        String userMail=account.getUserMail();
        String username=account.getUsername();
        //检验确认该房间存在:
//        if(roomService.findRoomByRoomName(roomName).isIfSuccess()){
//            return Result.fail("该房间不存在");
//        }

        ReservationRecord reservationRecord=new ReservationRecord(username,userMail,roomName,
                startTime,endTime, date,location);

        return reservationRecordService.validateReservationRecord(reservationRecord,userMail);
    }


    @GetMapping("/UserCheckSelfHistoryReservation")
    public List<ReservationRecord>selfHistoryReservation(){
        return
                reservationRecordService.findRecordsByUserMail
                        (LoginUserInfo.getAccount().getUserMail());
    }

    @GetMapping("/UserCheckDateSelfReservation")
    public List<ReservationRecord>selfDateReservation(@RequestParam Date date){
        List<ReservationRecord>list=reservationRecordService.findRecordsByUserMail
                (LoginUserInfo.getAccount().getUserMail());
        List<ReservationRecord>resultList=new ArrayList<>();
        for (ReservationRecord reservationRecord : list) {
            int check = reservationRecord.getDate().compareTo(date);
            if (check == 0) {
                resultList.add(reservationRecord);
            }
        }
        return resultList;
    }


    @GetMapping("/reservationRecordByRoomNameAndDate")
    public Result getRecordsByRoomNameAndDate(@RequestParam String roomName, @RequestParam Date date) {
        List<ReservationRecord> list = reservationRecordService.findALLByRoomNameAndDate(roomName, date);
        return Result.success(list);
    }



//    // 查询某个场地的所有预约记录
//    @GetMapping("/reservationRecordsByRoomName")
//    public Result getRecordsByRoomName(@RequestParam String roomName) {
//        List<ReservationRecord> list = reservationRecordService.findRecordsByRoomName(roomName);
//        Long tot = (long) list.size();
//        return Result.success(tot,SplitPage.splitList(list, PAGE_SIZE));
//    }

    //取消预约
    @PutMapping("/reservationCancel")
    public Result CancelReservation(@RequestParam long id){
        return reservationRecordService.UserCancelReservation(id);
    }


}
