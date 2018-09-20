package com.codecool.pionierzy.gotchiarena.service.LobbyServices;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;


public interface LobbyService {

    List<Room> getRooms();

    Room getOneRoom(String id);

    Room addRoom(String name, String username);

    Room joinRoom(Long id, String username);

    void deleteRoom(String name);

    void deleteAllRooms();
}
