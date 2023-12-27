package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.BuildingRepository;
import com.example.ooadgroupproject.entity.Building;
import com.example.ooadgroupproject.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public List<Building> findAll(){
        return buildingRepository.findAll();
    }

    @Override
    public Building save(Building building){
        return buildingRepository.save(building);
    }

    @Override
    public void deleteById(Integer id){
        buildingRepository.deleteById(id);
    }

    @Override
    public Building findByName(String name){return buildingRepository.findByName(name);}

    @Override
    public List<Building> search(String name){return buildingRepository.search(name);}

}
