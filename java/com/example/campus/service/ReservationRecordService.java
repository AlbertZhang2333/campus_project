package com.example.campus.service;

import com.example.campus.entity.Account;
import com.example.campus.entity.ReservationRecord;

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
