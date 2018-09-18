package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.List;


public interface LobbyService {

    List<Room> getRooms();

    Room addRoom(String name, String username);
/*
    boolean deleteRoom(String room);

    boolean joinRoom(String room, String user);
    */
}
