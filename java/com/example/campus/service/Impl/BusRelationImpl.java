package com.example.campus.service.Impl;

import com.example.campus.dao.BusRelationRepository;
import com.example.campus.entity.BusRelation;
import com.example.campus.service.BusRelationService;
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
    public BusRelation findByStartEnd(Integer startStop, Integer endStop){
        return busRelationRepository.findByStartEnd(startStop, endStop);
    }
}
