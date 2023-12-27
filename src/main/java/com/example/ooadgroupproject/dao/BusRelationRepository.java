package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Building;
import com.example.ooadgroupproject.entity.BusRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusRelationRepository extends JpaRepository<BusRelation, Integer> {
    @Query("select busRelation from BusRelation busRelation where busRelation.startStop = ?1 and busRelation.endStop = ?2")
    List<BusRelation> findByStartEnd(String start, String end);

    @Query("select busRelation from BusRelation busRelation where busRelation.startStop like %?1% or busRelation.endStop like %?1%")
    List<BusRelation> searchByStartOrEnd(String stop);

}
