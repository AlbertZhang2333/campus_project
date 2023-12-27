package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import com.example.ooadgroupproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.example.ooadgroupproject.service.ReservationRecordService;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ManageReservationRecord")
public class ManageReservationRecordController {
    private final int PAGE_SIZE = 5;
    @Autowired
    private ReservationRecordService reservationRecordService;
    @Autowired
    private EmailService emailService;
    //TODO
    @GetMapping("/reservationRecordsByLocation")
    public Result getRecordsByLocation(@RequestParam String location) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByLocation(location);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }
    //TODO
    @GetMapping("/reservationRecordsByUserMail")
    public Result getRecordsByUserMail(@RequestParam String userMail) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByUserMail(userMail);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }
    @DeleteMapping("/deleteAll")
    public Result deleteAll(){
        reservationRecordService.deleteAll();
        return Result.success("已成功删除所有预约！");
    }
    //TODO
    @GetMapping("/findAll")
    public Result findAll(){
        List<ReservationRecord> list = reservationRecordService.findAll();
        Long tot=(long)list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }
    //TODO
    @GetMapping("/reservationRecordByDate")
    public Result getRecordsByDate(@RequestParam Date date) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByDate(date);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
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

    //TODO
    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam long id){
        ReservationRecord reservationRecord=reservationRecordService.findRecordsById(id).orElse(null);
        if(reservationRecord == null){
            return Result.fail("未找到该预约！");
        }

        return reservationRecordService.deleteById(id);
    }

    //TODO
    @GetMapping("/findById")
    public Result findById(@RequestParam long id){
        ReservationRecord reservationRecord = reservationRecordService.findRecordsById(id).orElse(null);
        if (reservationRecord == null) {
            return Result.fail("未找到该预约！");
        }else return Result.success(reservationRecord);
    }



//    @Override
//    public List<ReservationRecord>findALLByRoomNameAndDate(String roomName, Date date){
//        if(date.compareTo(Date.valueOf(LocalDate.now()))<0){
//            return reservationRecordRepository.findAll();
//        }else {
//            return cacheClient.getReservationRecordList(roomName,date);
//        }
//    }
    //TODO
    @GetMapping("/reservationRecordsByRoomNameAndDate")
    public Result getRecordsByRoomNameAndDate(@RequestParam String roomName, @RequestParam Date date) {
        List<ReservationRecord> list = reservationRecordService.findALLByRoomNameAndDate(roomName, date);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }


    @PutMapping("/reservationCancel")
    public Result CancelReservation(@RequestParam Date date, @RequestParam long id, @RequestParam String userMail) {
        return reservationRecordService.CancelReservation(date,id,userMail);
    }




}
