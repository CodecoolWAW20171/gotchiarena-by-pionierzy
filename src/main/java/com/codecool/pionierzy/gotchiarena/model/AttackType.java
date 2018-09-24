package com.codecool.pionierzy.gotchiarena.model;

public enum AttackType {
    FIRE("fire"),
    WATER("water"),
    ICE("ice"),
    ELECTRIC("electric"),
    PLANT("plant"),
    GROUND("ground"),
    MAGIC("magic"),
    NORMAL("normal");

    private final String groupType;

    AttackType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupType() {
        return groupType;
    }
}
