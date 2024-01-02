package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.entity.BusLine;
import com.example.ooadgroupproject.service.BusLineService;
import com.example.ooadgroupproject.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusLineController {
    @Autowired
    private BusLineService busLineService;

    @GetMapping("/allLine")
    public Result findAll(){
        try {
            return Result.success(busLineService.findAll());
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

//    @GetMapping("/searchId/{id}")
//    public Result findById(@PathVariable Integer id){
//        try {
//            return Result.success(busLineService.findById(id));
//        } catch (Exception e) {
//            return Result.fail("查找失败");
//        }
//    }

    @GetMapping("/searchLineId/{lineId}")
    public Result findByLineId(@PathVariable Integer lineId){
        try {
            return Result.success(busLineService.findByLineId(lineId));
        } catch (Exception e) {
            return Result.fail("查找失败");
        }
    }

    @PostMapping("/addLine")
    public Result addOne(BusLine busLine) {
        try {
            return Result.success(busLineService.save(busLine));
        } catch (Exception e) {
            return Result.fail("添加失败");
        }
    }

    @PutMapping("/updateLine")
    public Result update(Integer id,
                          Integer LineId,
                          String startTime,
                          String endTime,
                          String direction,
                          Boolean inService,
                          Integer startStopId,
                          Integer endStopId){
        try {
            BusLine busLine = new BusLine();
            busLine.setId(id);
            busLine.setLineId(LineId);
            busLine.setStartTime(startTime);
            busLine.setEndTime(endTime);
            busLine.setDirection(direction);
            busLine.setInService(inService);
            busLine.setStartStopId(startStopId);
            busLine.setEndStopId(endStopId);
            return Result.success(busLineService.save(busLine));
        } catch (Exception e) {
            return Result.fail("添加失败");
        }
    }
    @DeleteMapping("/deleteLine/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try {
            busLineService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.fail("删除失败");
        }
    }

    /*@GetMapping("/isInService/{id}")
    public boolean isInService(@PathVariable Integer id){return busLineService.isInService(id);}

    @GetMapping("/lineInService")
    public List<BusLine> lineInService(){return busLineService.busLineInService();}*/
}
