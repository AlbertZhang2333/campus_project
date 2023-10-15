package com.example.campus.service;

import com.example.campus.entity.BusRelation;

import java.util.List;

public interface BusRelationService {
    public List<BusRelation> findAll();
    public BusRelation save(BusRelation BusRelation);
    public void deleteById(Integer id);
    public BusRelation findByStartEnd(Integer startStop, Integer endStop);
}
