package com.codecool.pionierzy.gotchiarena.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final long id;
    private final String name;
    private final List<Gotchi> gotchiList;


    public Player(long id, String name) {
        this.id = id;
        this.name = name;
        this.gotchiList = new ArrayList<>();
    }

    public void addTamagotchi(Gotchi gotchi) {
        gotchiList.add(gotchi);
    }

    public String getName() {
        return name;
    }
}
