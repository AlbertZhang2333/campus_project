package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.BusRelation;
import com.example.ooadgroupproject.service.BusRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusRelationController {
    @Autowired
    private BusRelationService busRelationService;

    @GetMapping("/allRelation")
    public Result findAll(){
        try {
            return Result.success(busRelationService.findAll());
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }
    @GetMapping("/startEndRelation/{startStop}/{endStop}")
    public Result findByStartEnd(@PathVariable String startStop,
                                            @PathVariable String endStop){
        try {
            return Result.success(busRelationService.findByStartEnd(startStop, endStop));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

    @PostMapping("/addRelation")
    public Result addOne(BusRelation busRelation) {
        try {
            return Result.success(busRelationService.save(busRelation));
        } catch (Exception e) {
            return Result.fail("添加失败");
        }
    }
    @PutMapping("/updateRelation")
    public Result update(@RequestParam Integer id,
                              @RequestParam String startStop,
                              @RequestParam String endStop,
                              @RequestParam Integer time,
                              @RequestParam Integer lineId,
                              @RequestParam String direction,
                              @RequestParam Integer stopNum){
        try {
            BusRelation busRelation = new BusRelation();
            busRelation.setId(id);
            busRelation.setStartStop(startStop);
            busRelation.setEndStop(endStop);
            busRelation.setTime(time);
            busRelation.setLineId(lineId);
            busRelation.setDirection(direction);
            busRelation.setStopNum(stopNum);
            return Result.success(busRelationService.save(busRelation));
        } catch (Exception e) {
            return Result.fail("添加失败");
        }
    }

    @DeleteMapping("/deleteRelation/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try {
            busRelationService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.fail("删除失败");
        }
    }

    @GetMapping("/searchByStartOrEnd/{stop}")
    public Result searchByStartOrEnd(@PathVariable String stop){
        try {
            return Result.success(busRelationService.searchByStartOrEnd(stop));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

}
