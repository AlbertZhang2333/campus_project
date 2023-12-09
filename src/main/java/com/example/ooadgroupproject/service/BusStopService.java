package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.BusStop;

import java.util.List;

public interface BusStopService {
    public List<BusStop> findAll();
    public BusStop save(BusStop BusStop);
    public void deleteById(Integer id);
    public BusStop findNearBusStop(Double lat, Double lng);
    public BusStop findByName(String name);

}
