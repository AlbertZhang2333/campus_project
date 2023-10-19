package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.entity.Room;
import com.example.ooadgroupproject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exer")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/room")
    public Room addOne(Room room) {
        return roomService.save(room);
    }

    @PutMapping("/room")
    public Room update(@RequestParam long id,
                       @RequestParam String roomName,
                       @RequestParam String location) {
        Room room = new Room();

        room.setId(id);
        room.setRoomName(roomName);
        room.setLocation(location);

        return roomService.save(room);
    }

    @DeleteMapping("/room/{id}")
    public void deleteOne(@PathVariable long id) {
        roomService.deleteById(id);
    }
}
