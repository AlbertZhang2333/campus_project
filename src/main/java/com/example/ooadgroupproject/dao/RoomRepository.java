package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    public Optional<Room>findRoomByRoomName(String roomName);
    public List<Room> findRoomByLocation(String location);

    public void deleteByRoomName(String roomName);


}
