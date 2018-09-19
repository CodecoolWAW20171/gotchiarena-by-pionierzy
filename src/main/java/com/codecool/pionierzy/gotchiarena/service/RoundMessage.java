package com.codecool.pionierzy.gotchiarena.service;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RoundMessage")
public class RoundMessage {
    private RoundAction ownerAction;

    private RoundAction opponentAction;
}
