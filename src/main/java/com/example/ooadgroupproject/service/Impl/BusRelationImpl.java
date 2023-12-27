package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.BusRelationRepository;
import com.example.ooadgroupproject.entity.BusRelation;
import com.example.ooadgroupproject.service.BusRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusRelationImpl implements BusRelationService {
    @Autowired
    private BusRelationRepository busRelationRepository;

    @Override
    public List<BusRelation> findAll(){
        return busRelationRepository.findAll();
    }

    @Override
    public BusRelation save(BusRelation busRelation){
        return busRelationRepository.save(busRelation);
    }

    @Override
    public void deleteById(Integer id){
        busRelationRepository.deleteById(id);
    }
    
    @Override
    public List<BusRelation> findByStartEnd(String startStop, String endStop){
        return busRelationRepository.findByStartEnd(startStop, endStop);
    }

    @Override
    public List<BusRelation> searchByStartOrEnd(String stop){
        return busRelationRepository.searchByStartOrEnd(stop);
    }
}
