package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
public class LobbyServiceImpl implements LobbyService {

    // Not sure if it has to be thread safe
    private final ConcurrentMap<String, Room> rooms;

    public LobbyServiceImpl() {
        this.rooms = new ConcurrentHashMap<>();
    }

    @Override
    public List<Room> getRooms() {
        return new LinkedList<>(rooms.values());
    }

    @Override
    public boolean addRoom(Room room) {
        return false;
    }

}
