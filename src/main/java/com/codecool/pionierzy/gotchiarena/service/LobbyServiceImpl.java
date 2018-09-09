package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LobbyServiceImpl implements LobbyService {

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
        if
        return false;
    }

}
