package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
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
    public Result findAll(){
        try {
            return Result.success(buildingService.findAll());
        } catch (Exception e){
            return Result.fail("Find all building failed");
        }
    }
    @GetMapping("/searchBuildingName/{name}")
    public Result findByName(@PathVariable String name){
        try {
            Building building = buildingService.findByName(name);
            return Result.success(building);
        } catch (Exception e){
            return Result.fail("Find building by name failed");
        }
    }
    @PostMapping("/addBuilding")
    public Result addOne(Building building) {
        try{
            return Result.success(buildingService.save(building));
        } catch (Exception e){
            return Result.fail("Add building failed");
        }
    }
    @PutMapping("/updateBuilding")
    public Result update(Integer id,
                           Double lat,
                           Double lng,
                           String name,
                           String description,
                           String photoPath,
                           String busStop){
        try{
            Building building = new Building();
            building.setId(id);
            building.setLat(lat);
            building.setLng(lng);
            building.setName(name);
            building.setDescription(description);
            building.setPhotoPath(photoPath);
            building.setBusStop(busStop);
            return Result.success(buildingService.save(building));
        } catch (Exception e){
            return Result.fail("Update building failed");
        }
    }

    @DeleteMapping("/deleteBuilding/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try {
            buildingService.deleteById(id);
            return Result.success("Delete building successfully");
        } catch (Exception e){
            return Result.fail("Delete building failed");
        }
    }
}
