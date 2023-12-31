package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Room;

import java.util.List;

public interface RoomService {
    // 保存或更新Room信息
//    public Room save(Room room);
    public void deleteById(long id);
    public Result createRoom(String roomName, String location,int capacity);

    Result updateRoom(String roomName, String location, int capacity);

    List<Room> findRoomByLocation(String location);

    Result findRoomByRoomName(String roomName);

    Result findRoomById(long id);

    List<Room> findAll();

    Result deleteRoomByRoomName(String roomName);

    List<String> checkLocations();

    List<Room> searchByRoomName(String roomName);

    List<Room> searchByRoomAndLocation(String roomName, String location);


    List<String> findAllLocations();
}
