package com.codecool.pionierzy.gotchiarena.service.LobbyServices;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.List;


public interface LobbyService {

    List<Room> getRooms();

    Room addRoom(String name, String username);

    Room joinRoom(Long id, String username);

    void deleteRoom(String name);

    void deleteAllRooms();
}
