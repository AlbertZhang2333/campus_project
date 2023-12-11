package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.entity.Building;
import com.example.ooadgroupproject.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    //return new Result
    @GetMapping("/allBuilding")
    public List<Building> findAll(){
        return buildingService.findAll();
    }
    @GetMapping("/searchBuildingName/{name}")
    public Building findByName(@PathVariable String name){return buildingService.findByName(name);}
    @PostMapping("/addBuilding")
    public Building addOne(Building building) { return buildingService.save(building); }
    @PutMapping("/updateBuilding")
    public Building update(Integer id,
                           Double lat,
                           Double lng,
                           String name,
                           String description,
                           String photoPath,
                           String busStop){
        Building building = new Building();
        building.setId(id);
        building.setLat(lat);
        building.setLng(lng);
        building.setName(name);
        building.setDescription(description);
        building.setPhotoPath(photoPath);
        building.setBusStop(busStop);
        return buildingService.save(building);
    }

    @DeleteMapping("/deleteBuilding/{id}")
    public void deleteOne(@PathVariable Integer id){buildingService.deleteById(id);}
}
