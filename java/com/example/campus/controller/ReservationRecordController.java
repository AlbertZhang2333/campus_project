package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequestMapping("/exer")
public class ReservationRecordController {
    @Autowired
    private ReservationRecordService reservationRecordService;

    @PostMapping("/reservationRecord")
    public ReservationRecord addOne(ReservationRecord reservationRecord) {
        return reservationRecordService.save(reservationRecord);
    }

    @PutMapping("/reservationRecord")
    public ReservationRecord update(@RequestParam long id,
                                    @RequestParam String userName,
                                    @RequestParam String userMail,
                                    @RequestParam String roomName,
                                    @RequestParam Time startTime,
                                    @RequestParam Time endTime,
                                    @RequestParam Date date,
                                    @RequestParam String location) {
        ReservationRecord reservationRecord = new ReservationRecord();
        reservationRecord.setId(id);
        reservationRecord.setUserName(userName);
        reservationRecord.setUserMail(userMail);
        reservationRecord.setRoomName(roomName);
        reservationRecord.setStartTime(startTime);
        reservationRecord.setEndTime(endTime);
        reservationRecord.setDate(date);
        reservationRecord.setLocation(location);

        return reservationRecordService.save(reservationRecord);
    }
}
