package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.BusLineRepository;
import com.example.ooadgroupproject.entity.BusLine;
import com.example.ooadgroupproject.service.BusLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BusLineImpl implements BusLineService {
    @Autowired
    private BusLineRepository busLineRepository;

    @Override
    public List<BusLine> findAll(){
        return busLineRepository.findAll();
    }

    @Override
    public BusLine save(BusLine busline){
        return busLineRepository.save(busline);
    }

    @Override
    public void deleteById(Integer id){
        busLineRepository.deleteById(id);
    }

    @Override
    public boolean isInService(Integer id){
        boolean res = busLineRepository.InService(id);
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
        String curTime = ft.format(new Date(System.currentTimeMillis()));
        String startTime = busLineRepository.getStartTime(id);
        String endTime = busLineRepository.getEndTime(id);
        res = res && (startTime.compareTo(curTime) < 0 && curTime.compareTo(endTime) < 0);
        return res;
    }
}
