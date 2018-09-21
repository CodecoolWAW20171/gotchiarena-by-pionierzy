package com.codecool.pionierzy.gotchiarena.model;

public enum AttackType {
    FIRE("wetFire"),
    WATER("wetFire"),
    ICE("wetFire"),
    LIGHTNING("bushyLightning"),
    PLANT("bushyLightning"),
    EARTH("bushyLightning");

    private final String groupType;

    AttackType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupType() {
        return groupType;
    }
}
