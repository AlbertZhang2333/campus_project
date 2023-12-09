package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Building;

import java.util.List;

public interface BuildingService {
    public List<Building> findAll();
    public Building findByName(String name);
    public Building save(Building building);
    public void deleteById(Integer id);
}
