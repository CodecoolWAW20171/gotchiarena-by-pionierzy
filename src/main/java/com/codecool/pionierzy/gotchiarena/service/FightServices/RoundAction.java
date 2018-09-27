package com.codecool.pionierzy.gotchiarena.service.FightServices;

public enum RoundAction {
    PRIMARY_ATTACK("attack"),
    SECONDARY_ATTACK("attack"),
    DEFEND("defend"),
    EVADE("defend");

    private final String groupType;

    RoundAction(String groupType) {
        this.groupType = groupType;
    }
}
