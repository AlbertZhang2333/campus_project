package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.BusLine;
import com.example.ooadgroupproject.entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusLineRepository extends JpaRepository<BusLine, Integer> {
    @Query("select busLine.id from BusLine busLine where busLine.id = ?1")
    Boolean InService(Integer id);
    @Query("select busLine.startTime from BusLine busLine where busLine.id = ?1")
    String getStartTime(Integer id);
    @Query("select busLine.endTime from BusLine busLine where busLine.id = ?1")
    String getEndTime(Integer id);

}
