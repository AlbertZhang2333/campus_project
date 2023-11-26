package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.BusStop;

import java.util.List;

public interface BusStopService {
    public List<BusStop> findAll();
    public List<BusStop> findByLine(Integer lineId);
    public BusStop save(BusStop BusStop);
    public void deleteById(Integer id);
    public List<BusStop> findNearBusStop(Integer lat, Integer lng);
    public BusStop findByName(String name);

}
