package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.entity.Building;
import com.example.ooadgroupproject.service.BuildingService;
import com.example.ooadgroupproject.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }
    @GetMapping("/searchBuildingName/{name}")
    public Result findByName(@PathVariable String name){
        try {
            return Result.success(buildingService.findByName(name));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }
    @PostMapping("/addBuilding")
    public Result addOne(Building building) {
        try {
            return Result.success(buildingService.save(building));
        } catch (Exception e) {
            return Result.fail("添加失败");
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
        try {
            Building building = new Building();
            building.setId(id);
            building.setLat(lat);
            building.setLng(lng);
            building.setName(name);
            building.setDescription(description);
            building.setPhotoPath(photoPath);
            building.setBusStop(busStop);
            return Result.success(buildingService.save(building));
        } catch (Exception e) {
            return Result.fail("添加失败");
        }
    }

    @DeleteMapping("/deleteBuilding/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try {
            buildingService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.fail("删除失败");
        }
    }

    @GetMapping("/searchingBuilding/{name}")
    public Result search(@PathVariable String name){
        try {
            return Result.success(buildingService.search(name));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

    @GetMapping("/allShowBuilding")
    public Result allShowBuilding(){
        try {
            List<Building> all = buildingService.findAll();
            List<Building> result = new ArrayList<>();
            for(Building b:all){
                if(b.getPhotoPath() != null && !b.getPhotoPath().equals("undefined") && b.getPhotoPath().length() > 0){
                    result.add(b);
                }
            }
            return Result.success(result);
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

}
