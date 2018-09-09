package com.codecool.pionierzy.gotchiarena.model;

public class Room {

    private final String name;
    private final Player owner;
    private Player opponent;


    public Room(String name, Player owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public boolean joinRoom(Player player) {
        if (opponent == null && !owner.equals(player)) {
            opponent = player;
            return true;
        }
        return false;
    }
}
