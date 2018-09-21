package com.codecool.pionierzy.gotchiarena.dao;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findRoomById(Long id);

    Room findRoomByOwnerUsername(String ownerName);

    Room findRoomByOwnerUsernameOrOpponentUsername(String ownerName, String opponentName);
}
