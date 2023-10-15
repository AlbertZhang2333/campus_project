package com.example.campus.service;

import com.example.campus.entity.BusLine;

import java.util.List;

public interface BusLineService {
    public List<BusLine> findAll();
    public BusLine save(BusLine busLine);
    public void deleteById(Integer id);
    public boolean isInService(Integer id);
}
