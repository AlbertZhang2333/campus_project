package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Room;
import com.example.ooadgroupproject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/getAllRooms")
    public Result getAllRooms() {
        return Result.success(roomService.findAll());
    }
    @GetMapping("/findRoomById")
    public Result findRoomById(@RequestParam long id) {
        return roomService.findRoomById(id);
    }

    @GetMapping("/findRoomByRoomName")
    public Result findRoomByRoomName(@RequestParam String roomName) {
        return roomService.findRoomByRoomName(roomName);
    }
    @GetMapping("/findRoomByLocation")
    public Result findRoomByLocation(@RequestParam String location) {
        return Result.success(roomService.findRoomByLocation(location));
    }
    @GetMapping("/checkLocations")
    public Result checkLocations() {
        List<String> locations = roomService.checkLocations();
        return Result.success(locations);
    }
    @GetMapping("/getAllLoaction")
    public Result getAllLoaction() {
        List<String> locations = roomService.findAllLocations();
        return Result.success(locations);
    }


}
