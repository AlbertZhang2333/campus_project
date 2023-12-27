package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
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
        } catch (Exception e){
            return Result.fail("Find all bus stop failed");
        }
    }
    @GetMapping("searchStopName/{name}")
    public Result findByName(@PathVariable String name){
        try {
            return Result.success(busStopService.findByName(name));
        } catch (Exception e){
            return Result.fail("Find bus stop by name failed");
        }
    }

    @GetMapping("nearbyStop")
    public Result findNearBusStop(@RequestParam Double lat,
                                         @RequestParam Double lng){
        try {
            return Result.success(busStopService.findNearBusStop(lat, lng));
        } catch (Exception e){
            return Result.fail("Find nearby bus stop failed");
        }
    }

    @PostMapping("/addStop")
    public Result addOne(BusStop busStop) {
        try {
            return Result.success(busStopService.save(busStop));
        } catch (Exception e){
            return Result.fail("Add bus stop failed");
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
        } catch (Exception e){
            return Result.fail("Update bus stop failed");
        }
    }

    @DeleteMapping("/deleteStop/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try {
            busStopService.deleteById(id);
            return Result.success("Delete bus stop successfully");
        } catch (Exception e){
            return Result.fail("Delete bus stop failed");
        }
    }

}
