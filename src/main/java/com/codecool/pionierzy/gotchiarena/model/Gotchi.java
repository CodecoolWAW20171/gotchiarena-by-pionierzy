package com.codecool.pionierzy.gotchiarena.model;

public class Gotchi {
    private int speed;
    private int defense;
    private int attack;
    private PokemonType type;

    public Gotchi(int speed, int defense, int attack, PokemonType type) {
        this.speed = speed;
        this.defense = defense;
        this.attack = attack;
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }
}
