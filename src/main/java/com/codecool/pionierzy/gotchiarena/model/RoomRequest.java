package com.codecool.pionierzy.gotchiarena.model;

public class RoomRequest {
    private String name;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Requesting new room: " + name;
    }
}
