package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ooadgroupproject.service.ReservationRecordService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/ManageReservationRecord")
public class ManageReservationRecordController {
    private final int PAGE_SIZE = 5;
    @Autowired
    private ReservationRecordService reservationRecordService;

    @GetMapping("/reservationRecordsByLocation")
    public Result getRecordsByLocation(@RequestParam String location) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByLocation(location);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }
    @GetMapping("/reservationRecordsByUserMail")
    public Result getRecordsByUserMail(@RequestParam String userMail) {
        List<ReservationRecord> list = reservationRecordService.findRecordsByUserMail(userMail);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }
    @PostMapping("/deleteAll")
    public void deleteAll(){
        reservationRecordService.deleteAll();
    }
    @GetMapping("/findAll")
    public List<ReservationRecord>findAll(){
        return reservationRecordService.findAll();
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



}
