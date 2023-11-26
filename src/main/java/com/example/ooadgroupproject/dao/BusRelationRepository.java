package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.BusRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusRelationRepository extends JpaRepository<BusRelation, Integer> {
    @Query("select busRelation from BusRelation busRelation where busRelation.startStop = ?1 and busRelation.endStop = ?2")
    BusRelation findByStartEnd(Integer start, Integer end);
}