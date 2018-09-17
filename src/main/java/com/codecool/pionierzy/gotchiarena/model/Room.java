package com.codecool.pionierzy.gotchiarena.model;

import java.util.UUID;

public class Room {

    private final UUID id;
    private final String name;
    private final Player owner;
    private Player opponent;


    public Room(String name, Player owner) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public int getCount() {
        return opponent != null ? 2 : 1;
    }

    public boolean joinRoom(Player player) {
        if (opponent == null && !owner.equals(player)) {
            opponent = player;
            return true;
        }
        return false;
    }
}
