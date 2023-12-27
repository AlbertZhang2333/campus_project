package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query("select building from Building building where building.name = ?1")
    public Building findByName(String name);

    @Query("select building from Building building where building.name like %?1%")
    public List<Building> search(String name);
}
