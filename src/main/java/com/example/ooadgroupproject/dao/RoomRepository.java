package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    public Optional<Room>findRoomByRoomName(String roomName);
    public List<Room> findRoomByLocation(String location);

    public void deleteByRoomName(String roomName);

    @Query("select room from Room room where room.roomName like %?1%")
    public List<Room> searchByRoomName(String roomName);

    @Query("select room from Room room where room.roomName like %?1% and room.location = ?2")
    public List<Room> searchByRoomAndLocation(String roomName, String location);

}
