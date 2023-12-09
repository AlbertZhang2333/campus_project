package com.example.ooadgroupproject.controller;

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
    public List<BusStop> findAll(){
        return busStopService.findAll();
    }
    @GetMapping("searchStopName/{name}")
    public BusStop findByName(@PathVariable String name){return busStopService.findByName(name);}

    @GetMapping("nearbyStop")
    public BusStop findNearBusStop(@RequestParam Double lat,
                                         @RequestParam Double lng){
        return busStopService.findNearBusStop(lat, lng);
    }

    @PostMapping("/addStop")
    public BusStop addOne(BusStop busStop) { return busStopService.save(busStop); }
    @PutMapping("/updateStop")
    public BusStop update(Integer id,
                          Double lat,
                          Double lng,
                          String name){
        BusStop busStop = new BusStop();
        busStop.setId(id);
        busStop.setLat(lat);
        busStop.setLng(lng);
        busStop.setName(name);
        return busStopService.save(busStop);
    }

    @DeleteMapping("/deleteStop/{id}")
    public void deleteOne(@PathVariable Integer id){busStopService.deleteById(id);}

}
