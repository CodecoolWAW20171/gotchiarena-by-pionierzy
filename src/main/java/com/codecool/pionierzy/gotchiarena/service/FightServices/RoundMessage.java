package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.codecool.pionierzy.gotchiarena.model.AttackType;
import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RoundMessage")
public class RoundMessage {
    private RoundAction ownerAction;
    private RoundAction opponentAction;
    private String ownerActionType;
    private String opponentActionType;

    private int ownerHPLoss;
    private int opponentHPLoss;

    public RoundMessage() {
        ownerAction = null;
        opponentAction = null;
    }

    public String getOwnerActionType() {
        return ownerActionType;
    }

    public String getOpponentActionType() {
        return opponentActionType;
    }

    public RoundAction getOwnerAction() {
        return ownerAction;
    }

    public void setOwnerAction(RoundAction ownerAction, Gotchi gotchi) {
        this.ownerAction = ownerAction;
        this.ownerActionType = getActionType(ownerAction, gotchi);
    }

    public RoundAction getOpponentAction() {
        return opponentAction;
    }

    public void setOpponentAction(RoundAction opponentAction, Gotchi gotchi) {
        this.opponentAction = opponentAction;
        this.opponentActionType = getActionType(opponentAction, gotchi);
    }


    private String getActionType(RoundAction action, Gotchi gotchi){
        switch (action){
            case PRIMARY_ATTACK:
                return gotchi.getType().toString();
            case SECONDARY_ATTACK:
                return gotchi.getSecondaryAttack().toString();
            default:
                return action.toString();
        }
    }

    public int getOwnerHPLoss() {
        return ownerHPLoss;
    }

    public void setOwnerHPLoss(int ownerHPLoss) {
        this.ownerHPLoss = ownerHPLoss;
    }

    public int getOpponentHPLoss() {
        return opponentHPLoss;
    }

    public void setOpponentHPLoss(int opponentHPLoss) {
        this.opponentHPLoss = opponentHPLoss;
    }
}
