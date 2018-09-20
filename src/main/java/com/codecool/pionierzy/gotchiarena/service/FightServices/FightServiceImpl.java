package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.codecool.pionierzy.gotchiarena.model.AttackType;
import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FightServiceImpl implements FightService {
    private Map<AttackType, AttackType> strongAgainst;
    private Map<AttackType, AttackType> weakAgainst;

    private HashMap<Room, RoundMessage> roomRoundMessageMap = new HashMap<Room, RoundMessage>();

    @Override
    public void receiveAction(Room room, User user, RoundAction action) {
        RoundMessage roundMessage = this.roomRoundMessageMap.get(room);
        if (user.equals(room.getOwner())) {
            roundMessage.setOwnerAction(action);
        }
        else {
            roundMessage.setOpponentAction(action);
        }
        if (roundMessage.getOwnerAction() != null && roundMessage.getOpponentAction() != null) {
            resolveRound(room, roundMessage);
        }
    }

    @Override
    public void resolveRound(Room room, RoundMessage roundMessage) {
        RoundAction ownerAction = roundMessage.getOwnerAction();
        RoundAction opponentAction = roundMessage.getOpponentAction();
        if (ownerAction == opponentAction) {
            if (ownerAction == RoundAction.DEFEND) {

            }
        }

        //after round we want to get a clear round
        this.roomRoundMessageMap.put(room, new RoundMessage());
    }

    @Override
    public RoundMessage sendResults(Room room) {
        return roomRoundMessageMap.get(room);
    }

    @Override
    public void startGame(Room room) {
        roomRoundMessageMap.put(room, new RoundMessage());
    }
}
