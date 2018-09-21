package com.codecool.pionierzy.gotchiarena.service.LobbyServices;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.List;


public interface LobbyService {

    List<Room> getRooms();

    Room getRoom(Long id);

    Long getRoomIdForUser(String username);

    Room addRoom(String username);

    Room joinRoom(Long id, String username);

    Room leaveRoom(Long id, String username);

    Long deleteRoom(Long id, String username);

    void deleteAllRooms();
}
