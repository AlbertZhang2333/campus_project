package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Room;

public interface RoomService {
    // 保存或更新Room信息
    public Room save(Room room);
    public void deleteById(long id);
}
