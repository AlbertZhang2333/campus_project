package com.example.campus.dao;

import com.example.campus.entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusStopRepository extends JpaRepository<BusStop, Integer> {
    @Query("select busStop from BusStop busStop where busStop.id = ?1")
    List<BusStop> findByLine(Integer lineId);

    @Query("select busStop from BusStop busStop where busStop.name = ?1")
    BusStop findByName(String name);

    boolean OnSameLine(Integer id1, Integer id2);
}
