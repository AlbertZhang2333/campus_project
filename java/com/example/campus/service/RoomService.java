package com.example.campus.service;

import com.example.campus.entity.Room;

public interface RoomService {
    // 保存或更新Room信息
    public Room save(Room room);

    public void deleteById(long id);
}
