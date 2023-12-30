package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.*;
import com.example.ooadgroupproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
//    @GetMapping("/commentSearchAdmin")
//    public Result getSearchAdmin(@RequestParam(required = false) String userMail,
//                                 @RequestParam(required = false) Date date,
//                                 @RequestParam(required = false) Integer belongDepartment,
//                                 @RequestParam(required = false) Integer type,
//                                 @RequestParam(required = false) int pageSize,
//                                 @RequestParam(required = false) int currentPage) {
//
//        List<Comment> list = commentService.findByConditions(userMail, date, CommentManagementDepartment.getByCode(belongDepartment), CommentType.getByCode(type));
//        Long tot = (long) list.size();
//
//        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
//    }

    //TODO
    @GetMapping("/reservationRecordsByLocation")
    public Result getRecordsByLocation(@RequestParam String location, @RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<ReservationRecord> list = reservationRecordService.findRecordsByLocation(location, pageable);
        Long tot = (long) list.getTotalElements();

        return Result.success(tot, list.getContent());
    }

    //TODO
    @GetMapping("/reservationRecordsByUserMail")
    public Result getRecordsByUserMail(@RequestParam String userMail, @RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<ReservationRecord> list = reservationRecordService.findRecordsByUserMail(userMail, pageable);
        Long tot = (long) list.getTotalElements();

        return Result.success(tot, list.getContent());
    }

    @DeleteMapping("/deleteAll")
    public Result deleteAll() {
        reservationRecordService.deleteAll();
        return Result.success("已成功删除所有预约！");
    }

    //TODO
    @GetMapping("/findAll")
    public Result findAll(@RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<ReservationRecord> list = reservationRecordService.findAll(pageable);
        Long tot = (long) list.getTotalElements();

        return Result.success(tot, list.getContent());
    }

    //TODO
    @GetMapping("/reservationRecordByDate")
    public Result getRecordsByDate(@RequestParam Date date, @RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<ReservationRecord> list = reservationRecordService.findRecordsByDate(date, pageable);
        Long tot = (long) list.getTotalElements();

        return Result.success(tot, list.getContent());
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
    public Result deleteById(@RequestParam long id) {
        ReservationRecord reservationRecord = reservationRecordService.findRecordsById(id).orElse(null);
        if (reservationRecord == null) {
            return Result.fail("未找到该预约！");
        }

        return reservationRecordService.deleteById(id);
    }

    //TODO
    @GetMapping("/findById")
    public Result findById(@RequestParam long id, @RequestParam int pageSize, @RequestParam int currentPage) {
        ReservationRecord reservationRecord = reservationRecordService.findRecordsById(id).orElse(null);
        if (reservationRecord == null) {
            return Result.fail("未找到该预约！");
        } else return Result.success(1L, reservationRecord);
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
    public Result getRecordsByRoomNameAndDate(@RequestParam String roomName, @RequestParam Date date,
                                              @RequestParam int pageSize, @RequestParam int currentPage) {
        List<ReservationRecord> list = reservationRecordService.findALLByRoomNameAndDate(roomName, date);
        Long tot = (long) list.size();
        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    //TODO
    @PutMapping("/reservationCancel")
    public Result CancelReservation(@RequestParam long id) {
        return reservationRecordService.AdminCancelReservation(id);
    }

}
