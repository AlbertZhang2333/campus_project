package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.dao.ReservationRecordRepository;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationRecordServiceImpl implements ReservationRecordService {
    @Autowired
    private ReservationRecordRepository reservationRecordRepository;

    @Autowired
    private CacheClient cacheClient;


    @Override
    public List<ReservationRecord> findRecordsByUserMail(String userMail) {
        return reservationRecordRepository.findByUserMail(userMail);
    }

    @Override
    public List<ReservationRecord> findRecordsByDate(Date date){
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
    @Override
    public synchronized Result validateReservationRecord(ReservationRecord reservationRecord,String userMail) {
        int compareResultDate = reservationRecord.getDate().compareTo(Date.valueOf(LocalDate.now()));
        int compareResultTime = reservationRecord.getStartTime().compareTo(Time.valueOf(LocalTime.now()));
        if (compareResultDate < 0 || (compareResultDate == 0 && compareResultTime < 0)) {
            return Result.fail("无法生成过时的预约记录");
        }
        int compareResult = reservationRecord.getStartTime().compareTo(reservationRecord.getEndTime());
        if (compareResult > 0) {
            return Result.fail("预约开始时间不能大于预约结束时间");
        }

        List<ReservationRecord> records = cacheClient.getReservationRecordList(
                reservationRecord.getRoomName(),
                reservationRecord.getDate());
        //去查缓存里能不能放的下。由于缓存内仅仅保存还未到期的预约，因此可以查询更少的数据完成任务
        int temp = records.size();
        if (records != null) {
                for (ReservationRecord record : records) {
                    if(record.getState()== ReservationState.Canceled){continue;}
                    boolean isConflict1 = false;
                    isConflict1 = (record.getStartTime().compareTo(reservationRecord.getStartTime()) <= 0
                            && record.getEndTime().compareTo(reservationRecord.getStartTime()) >= 0);
                    boolean isConflict2 = false;
                    isConflict2 = (record.getStartTime().compareTo(reservationRecord.getEndTime()) <= 0)
                            && (record.getEndTime().compareTo(reservationRecord.getEndTime()) >= 0);
                    if (isConflict1 || isConflict2) {
                        return Result.fail("预约时间冲突");
                    }
                }
            }
        long TTL = -Time.valueOf(LocalTime.now()).getTime() + reservationRecord.getEndTime().getTime()+3600*24*1000L;
        Random random = new Random();
        //用于规避一次需要删除过量的数据，减轻压力
        long r = random.nextLong(5000);
        //同步将该数据放入到缓存中
        cacheClient.setReservationRecord(reservationRecord, TTL + r,
                TimeUnit.MILLISECONDS);
        ReservationRecord reservationRecord1 = reservationRecordRepository.save(reservationRecord);
        return Result.success(reservationRecord1);
    }


    @Override
    public Result deleteByDateAndIdAndUserMail(Date date, long id, String userMail) {
        //删除一条预约信息，需要解决的问题有：这条预约是什么时候的？我需要先检查缓存，然后检查数据库。
        boolean cacheDelRes=cacheClient.deleteReservationRecord(date,id,userMail);
        reservationRecordRepository.deleteReservationRecordByDateAndIdAndUserMail
                (date,id,userMail);
        return Result.success("已成功取消预约");
    }
    @Override
    public Result CancelReservation(Date date, long id, String userMail) {
        //取消预约，需要解决的问题有：这条预约是什么时候的？我需要先检查缓存，然后检查数据库。
        boolean cacheDelRes=cacheClient.cancelReservationRecord(date,id,userMail);
        ReservationRecord reservationRecord=reservationRecordRepository.findById(id).orElse(null);
        if(reservationRecord==null){
            return Result.fail("找不到该预约记录");
        }else {
            reservationRecord.setState(ReservationState.Canceled);
            reservationRecordRepository.save(reservationRecord);
            //现在给用户发消息，告知其预约已被取消
            return Result.success("已成功取消预约");
        }

    }
    @Override
    public Result deleteById(long id) {
        //删除一条预约信息，需要解决的问题有：这条预约是什么时候的？我需要先检查缓存，然后检查数据库。
        ReservationRecord reservationRecord=reservationRecordRepository.findById(id).orElse(null);
        if(reservationRecord==null){
            return Result.fail("找不到该预约记录");
        }
        boolean cacheDelRes=cacheClient.deleteReservationRecord(reservationRecord.getDate(),id,reservationRecord.getUserMail());
        reservationRecordRepository.deleteReservationRecordByDateAndIdAndUserMail
                (reservationRecord.getDate(),id,reservationRecord.getUserMail());
        return Result.success("已成功取消预约");
    }

    @Override
    public void deleteAll(){
        boolean a=cacheClient.delete(CacheClient.RESERVATION_RECORD_KEY);
        reservationRecordRepository.deleteAll();
    }
    @Override
    public List<ReservationRecord> findAll(){
        return reservationRecordRepository.findAll();
    }
    @Override
    public List<ReservationRecord>findALLByRoomNameAndDate(String roomName, Date date){
        if(date.compareTo(Date.valueOf(LocalDate.now()))<0){
            return reservationRecordRepository.findAll();
        }else {
            return cacheClient.getReservationRecordList(roomName,date);
        }
    }

    @Override
    public Optional<ReservationRecord> findRecordsById(long id) {
        return reservationRecordRepository.findById(id);
    }

}
