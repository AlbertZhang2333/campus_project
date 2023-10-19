package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Room;

public interface RoomService {
    public Room save(Room room);

    public void deleteById(long id);
}
