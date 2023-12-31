package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.ReservationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.sql.Date;
import java.util.Optional;

public interface ReservationRecordService {
    //this function is used to find all Reservation records for some user.
    public Page<ReservationRecord> findRecordsByUserMail(String userMail, Pageable pageable);

    public List<ReservationRecord> findRecordsByUserMail(String userMail);

    public Page<ReservationRecord> findRecordsByDate(Date date, Pageable pageable);
    public List<ReservationRecord> findRecordsByDate(Date date);

    public Page<ReservationRecord> findRecordsByLocation(String location, Pageable pageable);

    public Page<ReservationRecord> findRecordsByRoomName(String roomName, Pageable pageable);

    //this function is used to insert a new reservation record.
    public ReservationRecord save(ReservationRecord reservationRecord);

    Result validateReservationRecord(ReservationRecord reservationRecord, String userMail);

    //TODO
    Result deleteByRoomNameAndDateAndIdAndUserMail(String roomName, Date date, long id, String userMail);
    Result AdminCancelReservation(long id);
    Result UserCancelReservation(long id);

    Result deleteById(long id);

    public void deleteAll();

    public Page<ReservationRecord>findAll(Pageable pageable);

    List<ReservationRecord>findALLByRoomNameAndDate(String roomName, Date date);

    Optional<ReservationRecord> findRecordsById(long id);

    List<String> findLocation();
}
