package com.example.campus.dao;

import com.example.campus.entity.Account;
import com.example.campus.entity.ReservationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRecordRepository extends JpaRepository<ReservationRecord, Long> {
    public List<ReservationRecord> findByUserMail(String userMail);

    public List<ReservationRecord> findByDate(Date date);

    public List<ReservationRecord> findByLocation(String location);

    public List<ReservationRecord> findByRoomName(String roomName);
}
