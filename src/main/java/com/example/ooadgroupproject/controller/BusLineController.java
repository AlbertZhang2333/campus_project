package com.example.ooadgroupproject.controller;

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
    public List<BusLine> findAll(){
        return busLineService.findAll();
    }

    @GetMapping("/searchLineId")
    public BusLine findById(Integer id){return busLineService.findById(id);}

    @PostMapping("/addLine")
    public BusLine addOne(BusLine busLine) { return busLineService.save(busLine); }

    @PostMapping("/updateLine")
    public BusLine update(Integer id,
                          Boolean inService,
                          String startTime,
                          String endTime,
                          Integer startStopId,
                          Integer endStopId){
        BusLine busLine = new BusLine();
        busLine.setId(id);
        busLine.setInService(inService);
        busLine.setStartTime(startTime);
        busLine.setEndTime(endTime);
        busLine.setStartStopId(startStopId);
        busLine.setEndStopId(endStopId);
        return busLineService.save(busLine);
    }
    @DeleteMapping("/deleteLine/{id}")
    public void deleteOne(@PathVariable Integer id){busLineService.deleteById(id);}

    @GetMapping("/isInService/{id}")
    public boolean isInService(@PathVariable Integer id){return busLineService.isInService(id);}

    @GetMapping("/lineInService")
    public List<BusLine> lineInService(){return busLineService.busLineInService();}
}
