package com.codecool.pionierzy.gotchiarena.dao;

import com.codecool.pionierzy.gotchiarena.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findRoomByName(String name);

    Room findRoomById(Long id);

}
