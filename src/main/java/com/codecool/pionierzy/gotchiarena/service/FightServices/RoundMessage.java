package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RoundMessage")
public class RoundMessage {
    private RoundAction ownerAction;

    private RoundAction opponentAction;

    private double ownerHPLoss;
    private double opponentHPLoss;

    public RoundMessage() {
        ownerAction = null;
        opponentAction = null;
    }

    public RoundAction getOwnerAction() {
        return ownerAction;
    }

    public void setOwnerAction(RoundAction ownerAction) {
        this.ownerAction = ownerAction;
    }

    public RoundAction getOpponentAction() {
        return opponentAction;
    }

    public void setOpponentAction(RoundAction opponentAction) {
        this.opponentAction = opponentAction;
    }

    public double getOwnerHPLoss() {
        return ownerHPLoss;
    }

    public void setOwnerHPLoss(int ownerHPLoss) {
        this.ownerHPLoss = ownerHPLoss;
    }

    public double getOpponentHPLoss() {
        return opponentHPLoss;
    }

    public void setOpponentHPLoss(int opponentHPLoss) {
        this.opponentHPLoss = opponentHPLoss;
    }
}
