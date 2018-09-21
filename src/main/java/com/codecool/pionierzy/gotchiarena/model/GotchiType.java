package com.codecool.pionierzy.gotchiarena.model;

public enum GotchiType {
    FIRE("wetFire"),
    WATER("wetFire"),
    ICE("wetFire"),
    LIGHTNING("bushyLightning"),
    PLANT("bushyLightning"),
    EARTH("bushyLightning");

    private final String groupType;

    GotchiType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupType() {
        return groupType;
    }
}
