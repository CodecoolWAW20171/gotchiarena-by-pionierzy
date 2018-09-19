package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;


public interface LobbyService {

    List<Room> getRooms();

    Room addRoom(String name, String username);

    ConcurrentMap<String, Room> getRoomsMap();
/*
    boolean deleteRoom(String room);

    boolean joinRoom(String room, String user);
    */
}
