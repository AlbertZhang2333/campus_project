package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.RedisLock;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.NumCountObject;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.dao.ReservationRecordRepository;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.ReservationRecord;
import com.example.ooadgroupproject.entity.ReservationState;
import com.example.ooadgroupproject.entity.Room;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.EmailService;
import com.example.ooadgroupproject.service.ReservationRecordService;
import com.example.ooadgroupproject.service.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
@Transactional
@Service
public class ReservationRecordServiceImpl implements ReservationRecordService {
    @Autowired
    private ReservationRecordRepository reservationRecordRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CacheClient cacheClient;
    @Autowired
    private EmailService emailService;


    @Override
    public Page<ReservationRecord> findRecordsByUserMail(String userMail, Pageable pageable) {
        return reservationRecordRepository.findByUserMail(userMail,pageable);
    }

    @Override
    public List<ReservationRecord> findRecordsByUserMail(String userMail) {
        return reservationRecordRepository.findByUserMail(userMail);
    }

    @Override
    public Page<ReservationRecord> findRecordsByDate(Date date, Pageable pageable){
        int compareResultDate = date.compareTo(Date.valueOf(LocalDate.now()));
        return reservationRecordRepository.findByDate(date,pageable);
    }
    @Override
    public List<ReservationRecord> findRecordsByDate(Date date) {
        return reservationRecordRepository.findByDate(date);
    }

    @Override
    public Page<ReservationRecord> findRecordsByLocation(String location, Pageable pageable) {
        return reservationRecordRepository.findByLocation(location,pageable);
    }

    @Override
    public Page<ReservationRecord> findRecordsByRoomName(String roomName, Pageable pageable) {
        return reservationRecordRepository.findByRoomName(roomName,pageable);
    }

    @Override
    public ReservationRecord save(ReservationRecord reservationRecord) {
        return reservationRecordRepository.save(reservationRecord);
    }
    @Override
    public Result validateReservationRecord(ReservationRecord reservationRecord,String userMail) {
        //尝试获取锁，如果不能获取到锁，则陷入等待：
        RedisLock lock=new RedisLock(reservationRecord.getLocation()+reservationRecord.getRoomName(),cacheClient.getRedisTemplate());
        LocalTime now = LocalTime.now();
        boolean res = false;
        while (LocalTime.now().getSecond() - now.getSecond() < 3000000) {
            res = lock.tryLock(1);
            if (res) break;
        }
        if(!res)return Result.fail("业务繁忙，请等候一下");
        int compareResultDate = reservationRecord.getDate().compareTo(Date.valueOf(LocalDate.now()));
        int compareResultTime = reservationRecord.getStartTime().compareTo(Time.valueOf(LocalTime.now()));
        if (compareResultDate < 0 || (compareResultDate == 0 && compareResultTime < 0)) {
            return Result.fail("无法生成过时的预约记录");
        }
        int compareResult = reservationRecord.getStartTime().compareTo(reservationRecord.getEndTime());
        if (compareResult > 0) {
            return Result.fail("预约开始时间不能大于预约结束时间");
        }
        LocalTime localStartTime = reservationRecord.getStartTime().toLocalTime();
        LocalTime localEndTime = reservationRecord.getEndTime().toLocalTime();
        if (ChronoUnit.MINUTES.between(localStartTime, localEndTime) > 120) {
            return Result.fail("预约时间要在2小时以内");
        }

        List<ReservationRecord> records = cacheClient.getReservationRecordList(
                reservationRecord.getRoomName(),
                reservationRecord.getDate());
        //去查缓存里能不能放的下。由于缓存内仅仅保存还未到期的预约，因此可以查询更少的数据完成任务
        int temp = records.size();
        if (records != null) {
                for (ReservationRecord record : records) {
                    if(record.getState()== ReservationState.Canceled){continue;}
                    LocalTime curLocalStartTime = record.getStartTime().toLocalTime();
                    LocalTime curLocalEndTime = record.getEndTime().toLocalTime();
                    if (!(ChronoUnit.MINUTES.between(curLocalEndTime, localStartTime) >= 0 ||
                            ChronoUnit.MINUTES.between(curLocalStartTime, localEndTime) <= 0)) {
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
        //异步更新数据库
        CompletableFuture.runAsync(()->{
            reservationRecordRepository.save(reservationRecord);
        });
        return Result.success(reservationRecord);
    }
    @Override
    public Result deleteByRoomNameAndDateAndIdAndUserMail(String roomName, Date date, long id, String userMail) {
        //删除一条预约信息，需要解决的问题有：这条预约是什么时候的？我需要先检查缓存，然后检查数据库。
        boolean cacheDelRes=cacheClient.deleteReservationRecord(roomName,date,id,userMail);
        reservationRecordRepository.deleteReservationRecordByDateAndIdAndUserMail
                (date,id,userMail);
        return Result.success("已成功删除预约");
    }
    @Override
    public Result AdminCancelReservation(long id) {
        //取消预约，需要解决的问题有：这条预约是什么时候的？我需要先检查缓存，然后检查数据库。
        ReservationRecord reservationRecord=reservationRecordRepository.findById(id).orElse(null);
        if(reservationRecord==null){
            return Result.fail("找不到该预约记录");
        }else {
            //更新缓存
            boolean cacheDelRes=cacheClient.cancelReservationRecord(reservationRecord.getRoomName(),reservationRecord.getDate()
                    ,id,reservationRecord.getUserMail());
            reservationRecord.setState(ReservationState.Canceled);
            reservationRecordRepository.save(reservationRecord);
            //现在给用户发消息，告知其预约已被取消
            emailService.sendReservationCanceledEmail(reservationRecord.getUserMail(),
                    reservationRecord.getRoomName(),reservationRecord.getDate());
            return Result.success("已成功取消预约");
        }
    }

    @Override
    public Result UserCancelReservation(long id) {
        //取消预约，需要解决的问题有：这条预约是什么时候的？我需要先检查缓存，然后检查数据库。
        ReservationRecord reservationRecord=reservationRecordRepository.findById(id).orElse(null);
        if(reservationRecord==null){
            return Result.fail("找不到该预约记录");
        }else {
            Account account= LoginUserInfo.getAccount();
            String userMail=account.getUserMail();
            if(!userMail.equals(reservationRecord.getUserMail())){
                return Result.fail("您无权修改该条预约记录!");
            }
            // 改缓存
            boolean cacheDelRes=cacheClient.cancelReservationRecord(reservationRecord.getRoomName(),reservationRecord.getDate()
                    ,id,userMail);
            reservationRecord.setState(ReservationState.Canceled);
            reservationRecordRepository.save(reservationRecord);
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
        boolean cacheDelRes=cacheClient.deleteReservationRecord(reservationRecord.getRoomName(),reservationRecord.getDate(),
                id,reservationRecord.getUserMail());
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
    public Page<ReservationRecord> findAll(Pageable pageable){
        return reservationRecordRepository.findAll(pageable);
    }
    @Override
    public List<ReservationRecord> findALLByRoomNameAndDate(String roomName, Date date){
        if(date.compareTo(Date.valueOf(LocalDate.now()))<=0){
            return reservationRecordRepository.findByRoomNameAndDate(roomName,date);
        }else {
            return cacheClient.getReservationRecordList(roomName,date);
        }
    }

    @Override
    public Optional<ReservationRecord> findRecordsById(long id) {
        return reservationRecordRepository.findById(id);
    }

    @Override
    public List<String> findLocation(){
        return reservationRecordRepository.findLocation();
    }

    @Override
    public NumCountObject[] findEveryRoomReservationNum(Date date){
        List<Room>roomList=roomService.findAll();
        NumCountObject[]countObjects=new NumCountObject[roomList.size()];
        for(int i=0;i<roomList.size();i++){
            List<ReservationRecord> recordList
                    =findALLByRoomNameAndDate(roomList.get(i).getRoomName(),date);
            countObjects[i]=new NumCountObject(roomList.get(i).getRoomName(),recordList.size());
        }
        return countObjects;

    }

}
