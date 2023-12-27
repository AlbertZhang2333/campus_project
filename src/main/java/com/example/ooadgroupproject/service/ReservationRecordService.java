package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.ReservationRecord;

import java.util.List;
import java.sql.Date;
import java.util.Optional;

public interface ReservationRecordService {
    //this function is used to find all Reservation records for some user.
    public List<ReservationRecord> findRecordsByUserMail(String userMail);

    public List<ReservationRecord> findRecordsByDate(Date date);

    public List<ReservationRecord> findRecordsByLocation(String location);

    public List<ReservationRecord> findRecordsByRoomName(String roomName);

    //this function is used to insert a new reservation record.
    public ReservationRecord save(ReservationRecord reservationRecord);

    Result validateReservationRecord(ReservationRecord reservationRecord, String userMail);

    public Result deleteByDateAndIdAndUserMail(Date date,long id, String userMail);

    Result CancelReservation(String roomName, Date date, long id, String userMail);

    Result deleteById(long id);

    public void deleteAll();

    public List<ReservationRecord>findAll();

    List<ReservationRecord>findALLByRoomNameAndDate(String roomName, Date date);

    Optional<ReservationRecord> findRecordsById(long id);
}
