package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.BusRelation;
import com.example.ooadgroupproject.entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusStopRepository extends JpaRepository<BusStop, Integer> {

    @Query("select busStop from BusStop busStop where busStop.name = ?1")
    BusStop findByName(String name);
    @Query("select busStop from BusStop busStop where busStop.name like %?1%")
    List<BusStop> search(String name);
}
