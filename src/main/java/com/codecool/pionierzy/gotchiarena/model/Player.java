package com.codecool.pionierzy.gotchiarena.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final long id;
    private final String name;
    private final List<Tamagotchi> tamagotchiList;


    public Player(long id, String name) {
        this.id = id;
        this.name = name;
        this.tamagotchiList = new ArrayList<>();
    }

    public void addTamagotchi(Tamagotchi tamagotchi) {
        tamagotchiList.add(tamagotchi);
    }

    public String getName() {
        return name;
    }
}
