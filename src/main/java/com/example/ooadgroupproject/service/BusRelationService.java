package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.BusRelation;

import java.util.List;

public interface BusRelationService {
    public List<BusRelation> findAll();
    public BusRelation save(BusRelation BusRelation);
    public void deleteById(Integer id);
    public List<BusRelation> findByStartEnd(String startStop, String endStop);

    public List<BusRelation> searchByStartOrEnd(String stop);
}
