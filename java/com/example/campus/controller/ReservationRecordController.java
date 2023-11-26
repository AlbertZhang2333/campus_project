package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import com.example.ooadgroupproject.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/Reservation")
public class ReservationRecordController {
    @Autowired
    private ReservationRecordService reservationRecordService;

    private final int PAGE_SIZE = 5;

//    @GetMapping("/hello")
//    public String hello() {
//        return "hello";
//    }

    @PostMapping("/reservationAdd")
    public ReservationRecord addOne(@RequestBody ReservationRecord reservationRecord) {
        return reservationRecordService.save(reservationRecord);
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


    // 查询某个具体用户的预约记录
    @GetMapping("/reservationRecordsByUserMail")
    public Result getRecordsByUserMail(@RequestParam String userMail) {
//        System.out.println(userMail);
        List<ReservationRecord> list = reservationRecordService.findRecordsByUserMail(userMail);
        Long tot = (long) list.size();
//        System.out.println(userMail);

        //        for (List<ReservationRecord> splitList : spiltLists) {
//            System.out.println("Split List: " + splitList);
//        }

//        return reservationRecordService.findRecordsByUserMail(userMail);
        return Result.success(tot, SplitPage.splitList(list, PAGE_SIZE));
    }

    // 查询某天的全部预约记录
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
