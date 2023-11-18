package com.example.campus.controller;

import com.example.campus.entity.ReservationRecord;
import com.example.campus.entity.ReservationState;
import com.example.campus.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;

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
                                    @RequestParam String location,
                                    @RequestParam ReservationState state) {
        ReservationRecord reservationRecord = new ReservationRecord();
        reservationRecord.setId(id);
        reservationRecord.setUserName(userName);
        reservationRecord.setUserMail(userMail);
        reservationRecord.setRoomName(roomName);
        reservationRecord.setStartTime(startTime);
        reservationRecord.setEndTime(endTime);
        reservationRecord.setDate(date);
        reservationRecord.setLocation(location);
        reservationRecord.setState(state);

        return reservationRecordService.save(reservationRecord);
    }

    // 查询某个具体用户的预约记录
    @GetMapping("/reservationRecordsByUserMail")
    public List<ReservationRecord> getRecordsByUserMail(@RequestParam String userMail) {
        return reservationRecordService.findRecordsByUserMail(userMail);
    }

    // 查询某天的全部预约记录
    @GetMapping("/reservationRecordsByDate")
    public List<ReservationRecord> getRecordsByDate(@RequestParam Date date) {
        return reservationRecordService.findRecordsByDate(date);
    }

    // 查询某栋建筑所有的预约记录
    @GetMapping("/reservationRecordsByLocation")
    public List<ReservationRecord> getRecordsByLocation(@RequestParam String location) {
        return reservationRecordService.findRecordsByLocation(location);
    }

    // 查询某个场地的所有预约记录
    @GetMapping("/reservationRecordsByRoomName")
    public List<ReservationRecord> getRecordsByRoomName(@RequestParam String roomName) {
        return reservationRecordService.findRecordsByRoomName(roomName);
    }
}
