package com.example.ooadgroupproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotNull
    private String roomName;
    @NotNull
    private String location;
    @Getter
    @Setter
    @NotNull
    private int capacity;

    public Room() {
    }
    public Room(String roomName, String location,int capacity) {
        this.roomName = roomName;
        this.location = location;
        this.capacity = capacity;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




}
