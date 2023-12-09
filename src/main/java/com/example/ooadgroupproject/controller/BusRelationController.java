package com.example.ooadgroupproject.controller;

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
    public List<BusRelation> findAll(){
        return busRelationService.findAll();
    }
    @GetMapping("/startEndRelation")
    public List<BusRelation> findByStartEnd(@RequestParam Integer startStop,
                                            @RequestParam Integer endStop){
        return busRelationService.findByStartEnd(startStop, endStop);
    }

    @PostMapping("/addRelation")
    public BusRelation addOne(BusRelation busRelation) { return busRelationService.save(busRelation); }
    @PutMapping("/updateRelation")
    public BusRelation update(@RequestParam Integer id,
                              @RequestParam Integer startStop,
                              @RequestParam Integer endStop,
                              @RequestParam Integer time,
                              @RequestParam Integer lineId){
        BusRelation busRelation = new BusRelation();
        busRelation.setId(id);
        busRelation.setStartStop(startStop);
        busRelation.setEndStop(endStop);
        busRelation.setTime(time);
        busRelation.setLineId(lineId);
        return busRelationService.save(busRelation);
    }

    @DeleteMapping("/deleteRelation/{id}")
    public void deleteOne(@PathVariable Integer id){busRelationService.deleteById(id);}


}
