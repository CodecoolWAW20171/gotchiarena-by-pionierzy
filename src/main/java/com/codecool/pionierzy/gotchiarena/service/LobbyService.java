package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.List;


public interface LobbyService {

    List<Room> getRooms();

    boolean addRoom(Room room);
/*
    boolean deleteRoom(String room);

    boolean joinRoom(String room, String user);
    */
}
