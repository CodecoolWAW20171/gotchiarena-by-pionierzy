package com.codecool.pionierzy.gotchiarena.model;

import java.util.UUID;

public class Room {

    private final UUID id;
    private final String name;

    // Storing users here seems quite unsafe (potentially exposed user data
    // since this is indirectly sent to users - see getOwner(), getOpponent())
    // Maybe don't send this data structure and define a new one
    private final User owner;
    private User opponent;


    public Room(String name, User owner) {
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

    public String getOwner() {
        return owner.getUsername();
    }

    public String getOpponent() {
        return opponent != null ? opponent.getUsername() : null;
    }

    public int getCount() {
        return opponent != null ? 2 : 1;
    }

    public boolean joinRoom(User user) {
        if (opponent == null && !owner.equals(user)) {
            opponent = user;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Room: %s (%d/2). Owner: %s", name, getCount(), owner.getUsername());
    }
}
