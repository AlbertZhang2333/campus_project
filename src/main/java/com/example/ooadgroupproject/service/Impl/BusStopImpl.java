package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.BusStopRepository;
import com.example.ooadgroupproject.entity.BusStop;
import com.example.ooadgroupproject.service.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class BusStopImpl implements BusStopService {
    @Autowired
    private BusStopRepository busStopRepository;

    @Override
    public List<BusStop> findAll(){
        return busStopRepository.findAll();
    }

    @Override
    public BusStop save(BusStop busStop){
        return busStopRepository.save(busStop);
    }

    @Override
    public void deleteById(Integer id){
        busStopRepository.deleteById(id);
    }

    @Override
    public BusStop findByName(String name){return busStopRepository.findByName(name);}

    @Override
    public BusStop findNearBusStop(Double lat, Double lng){
        List<BusStop> busStopList = busStopRepository.findAll();
        int len = busStopList.size();
        double minDistance = Double.MAX_VALUE;
        BusStop stop = null;
        for(int i = 0; i < len; i++){
            BusStop curBusStop = busStopList.get(i);
            double distance = Math.sqrt(Math.pow(curBusStop.getLat() - lat, 2) + Math.pow(curBusStop.getLng() - lng, 2));
            if(distance < minDistance) {
                minDistance = distance;
                stop = curBusStop;
            }
        }
        return stop;
    }

}
