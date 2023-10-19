package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.dao.ReservationRecordRepository;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.ReservationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationRecordServiceImpl implements ReservationRecordService {
    @Autowired
    private ReservationRecordRepository reservationRecordRepository;

    @Override
    public List<ReservationRecord> findUserAllReservationRecord(String userMail) {
        return reservationRecordRepository.findUserReservationRecord(userMail);
    }

    @Override
    public ReservationRecord save(ReservationRecord reservationRecord) {
        return reservationRecordRepository.save(reservationRecord);
    }
}
