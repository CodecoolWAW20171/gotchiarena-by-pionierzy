package com.codecool.pionierzy.gotchiarena.model;

public class Room {

    private final long id;
    private final String name;
    private final Player owner;
    private Player opponent;


    public Room(String name, Player owner) {
        this.id = 1;
        this.name = name;
        this.owner = owner;
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
