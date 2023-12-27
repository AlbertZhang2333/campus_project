package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.BusLine;
import com.example.ooadgroupproject.service.BusLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class BusLineController {
    @Autowired
    private BusLineService busLineService;

    @GetMapping("/allLine")
    public Result findAll(){
        try {
            return Result.success(busLineService.findAll());
        } catch (Exception e){
            return Result.fail("Find all bus line failed");
        }
    }

    @GetMapping("/searchLineId")
    public Result findById(Integer id){
        try{
            return Result.success(busLineService.findById(id));
        } catch (Exception e){
            return Result.fail("Find bus line by id failed");
        }
    }

    @PostMapping("/addLine")
    public Result addOne(BusLine busLine) {
        try {
            return Result.success(busLineService.save(busLine));
        } catch (Exception e){
            return Result.fail("Add bus line failed");
        }
    }

    @PostMapping("/updateLine")
    public Result update(Integer id,
                          Boolean inService,
                          String startTime,
                          String endTime,
                          Integer startStopId,
                          Integer endStopId){
        try {
            BusLine busLine = new BusLine();
            busLine.setId(id);
            busLine.setInService(inService);
            busLine.setStartTime(startTime);
            busLine.setEndTime(endTime);
            busLine.setStartStopId(startStopId);
            busLine.setEndStopId(endStopId);
            return Result.success(busLineService.save(busLine));
        } catch (Exception e){
            return Result.fail("Update bus line failed");
        }
    }
    @DeleteMapping("/deleteLine/{id}")
    public Result deleteOne(@PathVariable Integer id){
        try {
            busLineService.deleteById(id);
            return Result.success("Delete bus line successfully");
        } catch (Exception e){
            return Result.fail("Delete bus line failed");
        }
    }

    @GetMapping("/isInService/{id}")
    public boolean isInService(@PathVariable Integer id){return busLineService.isInService(id);}

    @GetMapping("/lineInService")
    public List<BusLine> lineInService(){return busLineService.busLineInService();}
}
