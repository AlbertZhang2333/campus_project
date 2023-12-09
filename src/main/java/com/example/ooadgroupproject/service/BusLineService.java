package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.BusLine;

import java.util.List;

public interface BusLineService {
    public List<BusLine> findAll();
    public BusLine save(BusLine busLine);
    public void deleteById(Integer id);
    public boolean isInService(Integer id);
    public BusLine findById(Integer id);
    public List<BusLine> busLineInService();
}
