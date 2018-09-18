package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.dao.UserRepository;
import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
public class LobbyServiceImpl implements LobbyService {

    private final UserRepository userRepository;

    // Not sure if it has to be thread safe
    private final ConcurrentMap<String, Room> rooms;

    @Autowired
    public LobbyServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.rooms = new ConcurrentHashMap<>();
    }

    @Override
    public List<Room> getRooms() {
        return new LinkedList<>(rooms.values());
    }

    @Override
    public Room addRoom(String name, String username) {
        if (rooms.containsKey(name)) return null;
        User owner = userRepository.findByUsername(username);
        if (owner == null) return null;
        Room room = new Room(name, owner);
        rooms.put(name, room);
        return room;
    }

}
