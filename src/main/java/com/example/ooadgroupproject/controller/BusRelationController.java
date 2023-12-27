package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.BusRelation;
import com.example.ooadgroupproject.service.BusRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Relation;
import java.util.List;

@RestController
public class BusRelationController {
    @Autowired
    private BusRelationService busRelationService;

    @GetMapping("/allRelation")
    public Result findAll(){
        try {
            return Result.success(busRelationService.findAll());
        } catch (Exception e){
            return Result.fail("Find all bus relation failed");
        }
    }
    @GetMapping("/startEndRelation")
    public Result findByStartEnd(@RequestParam Integer startStop,
                                            @RequestParam Integer endStop){
        try {
            return Result.success(busRelationService.findByStartEnd(startStop, endStop));
        } catch (Exception e){
            return Result.fail("Find bus relation by start and end failed");
        }
    }

    @PostMapping("/addRelation")
    public Result addOne(BusRelation busRelation) {
        try {
            return Result.success(busRelationService.save(busRelation));
        } catch (Exception e){
            return Result.fail("Add bus relation failed");
        }
    }
    @PutMapping("/updateRelation")
    public Result update(@RequestParam Integer id,
                              @RequestParam Integer startStop,
                              @RequestParam Integer endStop,
                              @RequestParam Integer time,
                              @RequestParam Integer lineId){
        try{
            BusRelation busRelation = new BusRelation();
            busRelation.setId(id);
            busRelation.setStartStop(startStop);
            busRelation.setEndStop(endStop);
            busRelation.setTime(time);
            busRelation.setLineId(lineId);
            return Result.success(busRelationService.save(busRelation));
        } catch (Exception e){
            return Result.fail("Update bus relation failed");
        }
    }

    @DeleteMapping("/deleteRelation/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try{
            busRelationService.deleteById(id);
            return Result.success();
        } catch (Exception e){
            return Result.fail("Delete bus relation failed");
        }
    }


}
