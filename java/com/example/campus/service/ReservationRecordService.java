package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.ReservationRecord;

import java.util.*;

public interface ReservationRecordService {
    //this function is used to find all Reservation records for some user.
    public List<ReservationRecord> findUserAllReservationRecord(String userName);

    //this function is used to insert a new reservation record.
    public ReservationRecord save(ReservationRecord reservationRecord);

}
