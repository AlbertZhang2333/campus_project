package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.dao.RoomRepository;
import com.example.ooadgroupproject.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteById(long id) {
        roomRepository.deleteById(id);
    }
}
