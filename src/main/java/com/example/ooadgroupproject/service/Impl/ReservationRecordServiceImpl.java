package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.ReservationRecordRepository;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReservationRecordServiceImpl implements ReservationRecordService {
    @Autowired
    private ReservationRecordRepository reservationRecordRepository;

    @Override
    public List<ReservationRecord> findRecordsByUserMail(String userMail) {
        return reservationRecordRepository.findByUserMail(userMail);
    }

    @Override
    public List<ReservationRecord> findRecordsByDate(Date date) {
        return reservationRecordRepository.findByDate(date);
    }

    @Override
    public List<ReservationRecord> findRecordsByLocation(String location) {
        return reservationRecordRepository.findByLocation(location);
    }

    @Override
    public List<ReservationRecord> findRecordsByRoomName(String roomName) {
        return reservationRecordRepository.findByRoomName(roomName);
    }

    @Override
    public ReservationRecord save(ReservationRecord reservationRecord) {
        return reservationRecordRepository.save(reservationRecord);
    }
}
