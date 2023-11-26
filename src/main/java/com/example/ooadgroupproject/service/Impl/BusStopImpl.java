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
    public List<BusStop> findByLine(Integer lineId){
        return busStopRepository.findByLine(lineId);
    }

    @Override
    public BusStop findByName(String name){return busStopRepository.findByName(name);}

    @Override
    public List<BusStop> findNearBusStop(Integer lat, Integer lng){
        List<BusStop> busStopList = busStopRepository.findAll();
        PriorityQueue<Distance> pq = new PriorityQueue<>(2);
        int len = busStopList.size();
        for(int i = 0; i < len; i++){
            BusStop curBusStop = busStopList.get(i);
            double distance = Math.sqrt(Math.pow(curBusStop.getLat() - lat, 2) + Math.pow(curBusStop.getLng() - lng, 2));
            Distance curDistance = new Distance(curBusStop.getId(), distance);
            if(pq.size() < 2){
                pq.add(curDistance);
            }else{
                pq.poll();
                pq.add(curDistance);
            }
        }
        List<BusStop> res = new ArrayList<>();
        int l = Math.min(2, pq.size());
        for(int i = 0; i < l; i++){
            Distance cur = pq.poll();
            if(busStopRepository.findById(cur.id).isPresent()){
                BusStop curBusStop = busStopRepository.findById(cur.id).get();
                res.add(curBusStop);
            }
        }
        return res;
    }


    private class Distance{
        int id;
        double distance;
        public Distance(int id, double distance){
            this.id = id;
            this.distance = distance;
        }
    }
}
