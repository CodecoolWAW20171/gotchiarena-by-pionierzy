package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.dao.RoomRepository;
import com.codecool.pionierzy.gotchiarena.dao.UserRepository;
import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;



@Service
public class LobbyServiceImpl implements LobbyService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public LobbyServiceImpl(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getOneRoom(String idString){
        Long id = Long.valueOf(idString);
        return roomRepository.findRoomById(id);
    }

    @Override
    public Room addRoom(String name, String username) {
        if (roomRepository.findRoomByName(name) != null) return null;
        User owner = userRepository.findByUsername(username);
        if (owner == null) return null;
        Room room = new Room(name, owner);
        roomRepository.save(room);
        return room;
    }

    @Override
    public void deleteRoom(String name) {
    }

    @Override
    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }

    @Override
    public Room joinRoom(Long id, String username) {
        Room room = roomRepository.findRoomById(id);
        User user = userRepository.findByUsername(username);
        if (room == null || user == null) return null;
        if (room.joinRoom(user)) {
            roomRepository.save(room);
            return room;
        }
        return null;
    }

}
