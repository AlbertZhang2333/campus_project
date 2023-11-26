package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.ReservationRecord;

import java.util.List;
import java.sql.Date;

public interface ReservationRecordService {
    //this function is used to find all Reservation records for some user.
    public List<ReservationRecord> findRecordsByUserMail(String userMail);

    public List<ReservationRecord> findRecordsByDate(Date date);

    public List<ReservationRecord> findRecordsByLocation(String location);

    public List<ReservationRecord> findRecordsByRoomName(String roomName);

    //this function is used to insert a new reservation record.
    public ReservationRecord save(ReservationRecord reservationRecord);
}
