package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Building;
import com.example.ooadgroupproject.entity.BusStop;
import com.example.ooadgroupproject.service.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusStopController {
    @Autowired
    private BusStopService busStopService;
    @GetMapping("/allStop")
    public Result findAll(){
        try {
            return Result.success(busStopService.findAll());
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }
    @GetMapping("/searchStopName/{name}")
    public Result findByName(@PathVariable String name){
        try {
            return Result.success(busStopService.findByName(name));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

    @GetMapping("/nearbyStop")
    public Result findNearBusStop(@RequestParam Double lat,
                                         @RequestParam Double lng){
        try {
            return Result.success(busStopService.findNearBusStop(lat, lng));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

    @PostMapping("/addStop")
    public Result addOne(BusStop busStop) {
        try {
            return Result.success(busStopService.save(busStop));
        } catch (Exception e) {
            return Result.fail("添加失败");
        }
    }
    @PutMapping("/updateStop")
    public Result update(Integer id,
                          Double lat,
                          Double lng,
                          String name){
        try {
            BusStop busStop = new BusStop();
            busStop.setId(id);
            busStop.setLat(lat);
            busStop.setLng(lng);
            busStop.setName(name);
            return Result.success(busStopService.save(busStop));
        } catch (Exception e) {
            return Result.fail("添加失败");
        }
    }

    @DeleteMapping("/deleteStop/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try {
            busStopService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.fail("删除失败");
        }
    }
    @GetMapping("/searchingBusStop/{name}")
    public Result search(@PathVariable String name){
        try {
            return Result.success(busStopService.search(name));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

}
