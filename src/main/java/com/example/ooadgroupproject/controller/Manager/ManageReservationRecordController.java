package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.ReservationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ooadgroupproject.service.ReservationRecordService;

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
}
