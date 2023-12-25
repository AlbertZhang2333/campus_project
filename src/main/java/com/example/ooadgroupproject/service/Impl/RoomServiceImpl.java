package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.dao.RoomRepository;
import com.example.ooadgroupproject.entity.Room;
import com.example.ooadgroupproject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

//    @Override
//    public Room save(Room room) {
//        return roomRepository.save(room);
//    }

    @Override
    public void deleteById(long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Result createRoom(String roomName, String location,int capacity){
        Room room=roomRepository.findRoomByRoomName(roomName).orElse(null);
        if(room!=null){
            return Result.fail("计划创建的房间已存在!");
        }
        if(capacity<=0){
            return Result.fail("房间容量必须大于0!");
        }
        Room newRoom=new Room(roomName,location,capacity);
        roomRepository.save(newRoom);
        return Result.success("房间"+newRoom.getRoomName()+"已创建！");
    }

    @Override
    public Result updateRoom(String roomName, String location, int capacity){
        Room room=roomRepository.findRoomByRoomName(roomName).orElse(null);
        if(room!=null){
            room.setLocation(location);
            room.setCapacity(capacity);
            roomRepository.save(room);
            return Result.success("房间"+room.getRoomName()+"已更新！");
        }else{
            return Result.fail("计划更新的房间不存在!");
        }
    }

    @Override
    public List<Room> findRoomByLocation(String location){
        return roomRepository.findRoomByLocation(location);
    }

    @Override
    public Result findRoomByRoomName(String roomName){
        return Result.success(roomRepository.findRoomByRoomName(roomName));
    }
    @Override
    public Result findRoomById(long id){
        return Result.success(roomRepository.findById(id));
    }
    @Override
    public List<Room> findAll(){
        return roomRepository.findAll();
    }
    @Override
    public Result deleteRoomByRoomName(String roomName){
        Room room=roomRepository.findRoomByRoomName(roomName).orElse(null);
        if(room!=null){
            roomRepository.deleteByRoomName(roomName);
            return Result.success("房间"+room.getRoomName()+"已删除！");
        }else{
            return Result.fail("计划删除的房间不存在!");
        }
    }

    @Override
    public List<String>checkLocations(){
        return roomRepository.findAll().stream().map(Room::getLocation).toList();
    }

}
