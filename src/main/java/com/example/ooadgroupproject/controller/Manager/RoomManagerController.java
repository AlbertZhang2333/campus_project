package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ManageRoom")
public class RoomManagerController {
    @Autowired
    RoomService roomService;
    @RequestMapping("/createRoom")
    public Result createRoom(@RequestParam String roomName, @RequestParam String location, @RequestParam int capacity) {
        return roomService.createRoom(roomName, location, capacity);
    }
    @RequestMapping("/updateRoom")
    public Result updateRoom(@RequestParam String roomName,@RequestParam String location, @RequestParam int capacity) {
        return roomService.updateRoom(roomName, location, capacity);
    }

    @RequestMapping("/findRoomByRoomName")
    public Result findRoomByRoomName(@RequestParam String roomName) {
        return roomService.findRoomByRoomName(roomName);
    }

    @RequestMapping("/findRoomById")
    public Result findRoomById(@RequestParam long id) {
        return roomService.findRoomById(id);
    }
    @RequestMapping("/findRoomByLocation")
    public Result findRoomByLocation(@RequestParam String location) {
        return Result.success(roomService.findRoomByLocation(location));
    }
    @DeleteMapping("/deleteRoomByRoomName")
    public Result deleteRoomByRoomName(@RequestParam String roomName) {
        return roomService.deleteRoomByRoomName(roomName);
    }
    @RequestMapping("/findAll")
    public Result findAll() {
        return Result.success(roomService.findAll());
    }
    @RequestMapping("/checkLocations")
    public Result checkLocations() {
        List<String> locations= roomService.checkLocations();
        return Result.success(locations);
    }

    @GetMapping("/searchByRoomName")
    public Result searchByRoomName(@RequestParam String roomName) {
        try {
            return Result.success(roomService.searchByRoomName(roomName));
        } catch (Exception e) {
            return Result.fail("查询失败");
        }
    }

    @GetMapping("/searchByRoomAndLocation")
    public Result searchByRoomAndLocation(@RequestParam String roomName, @RequestParam String location) {
        try {
            return Result.success(roomService.searchByRoomAndLocation(roomName, location));
        } catch (Exception e) {
            return Result.fail("查询失败");
        }
    }
}
