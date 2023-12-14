package com.example.ooadgroupproject.service.Impl;

import cn.hutool.json.JSONUtil;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.dao.ReservationRecordRepository;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    public Result validateReservationRecord(ReservationRecord reservationRecord,String userMail){
        int compareResultDate=reservationRecord.getDate().compareTo(Date.valueOf(LocalDate.now()));
        int compareResultTime=reservationRecord.getStartTime().compareTo(Time.valueOf(LocalTime.now()));
        if(compareResultDate<0||(compareResultDate==0&&compareResultTime<0)){
            return Result.fail("无法生成过时的预约记录");
        }
        int compareResult=reservationRecord.getStartTime().compareTo(reservationRecord.getEndTime());
        if(compareResult>0){
            return Result.fail("预约开始时间不能大于预约结束时间");
        }

        //现在将增添，确保将要放入的时段中，仅仅目前还没有人预约，确保不存在预约时间冲突//后续将改为查缓存
        for(ReservationRecord record:reservationRecordRepository.findReservationRecordByDateAndRoomName
                (reservationRecord.getDate(),reservationRecord.getRoomName())){
            boolean isConflict1=false;
            isConflict1=(record.getStartTime().compareTo(reservationRecord.getStartTime())<=0
                    &&record.getEndTime().compareTo(reservationRecord.getStartTime())>=0);
            boolean isConflict2=false;
            isConflict2=(record.getStartTime().compareTo(reservationRecord.getEndTime())<=0)
                    &&(record.getEndTime().compareTo(reservationRecord.getEndTime())>=0);
            if(isConflict1||isConflict2) {
                return Result.fail("预约时间冲突");
            }
        }
        ReservationRecord reservationRecord1=reservationRecordRepository.save(reservationRecord);
        long TTL=Time.valueOf(LocalTime.now()).getTime()-reservationRecord1.getStartTime().getTime();

        //同步将该数据放入到缓存中
        cacheClient.set(CacheClient.RESERVATION_RECORD_KEY
                        +String.valueOf(reservationRecord1.getId())
                        +userMail
                , JSONUtil.toJsonStr(reservationRecord1),TTL+1000L,TimeUnit.MILLISECONDS);
        return Result.success("预约成功!");
    }

    @Override
    public Result deleteByIdAndUserMail(long id,String userMail) {
        //删除一条预约信息，需要解决的问题有：这条预约是什么时候的？我需要先检查缓存，然后检查数据库。

        boolean cacheDelRes=cacheClient.delete
                (CacheClient.RESERVATION_RECORD_KEY+String.valueOf(id)+userMail);
        reservationRecordRepository.deleteReservationRecordByIdAndUserMail(id,userMail);
        return Result.success("已成功取消预约");
    }

}
