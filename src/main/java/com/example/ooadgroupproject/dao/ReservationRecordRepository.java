package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.ReservationRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRecordRepository extends JpaRepository<ReservationRecord, Long>, PagingAndSortingRepository<ReservationRecord, Long> {
    public void deleteAllBy();

    public Page<ReservationRecord> findByUserMail(String userMail, Pageable pageable);

    public List<ReservationRecord> findByUserMail(String userMail);

    public Page<ReservationRecord> findByDate(Date date, Pageable pageable);

    public List<ReservationRecord> findByDate(Date date);

    public Page<ReservationRecord> findByLocation(String location, Pageable pageable);

    public Page<ReservationRecord> findByRoomName(String roomName, Pageable pageable);

    public Page<ReservationRecord>
    findReservationRecordByDateAndRoomNameAndLocation(Date date,
                                                      String roomName, String location, Pageable pageable);

    public void deleteReservationRecordByDateAndIdAndUserMail(Date date, long id, String userMail);

    @NotNull
    public Page<ReservationRecord> findAll(@NotNull Pageable pageable);

    public List<ReservationRecord> findByRoomNameAndDate(String roomName, Date date);

    @Query("select distinct reservation_record.location from ReservationRecord reservation_record")
    public List<String> findLocation();


}
