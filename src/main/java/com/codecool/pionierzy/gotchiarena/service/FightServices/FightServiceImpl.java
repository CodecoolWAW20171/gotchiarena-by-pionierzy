package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.codecool.pionierzy.gotchiarena.model.AttackType;
import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.HashMap;
import java.util.Map;

public class FightServiceImpl implements FightService {
    private Map<AttackType, AttackType> strongAgainst;
    private Map<AttackType, AttackType> weakAgainst;

    private HashMap<Room, RoundMessage> roomMap;

    public FightServiceImpl() {
        strongAgainst = new HashMap<AttackType, AttackType>();
        weakAgainst = new HashMap<AttackType, AttackType>();

        strongAgainst.put(AttackType.LIGHTNING, AttackType.PLANT);
        strongAgainst.put(AttackType.PLANT, AttackType.EARTH);
        strongAgainst.put(AttackType.EARTH, AttackType.LIGHTNING);
        strongAgainst.put(AttackType.FIRE, AttackType.ICE);
        strongAgainst.put(AttackType.ICE, AttackType.WATER);
        strongAgainst.put(AttackType.WATER, AttackType.FIRE);
        for (Map.Entry<AttackType, AttackType> entry : strongAgainst.entrySet()) {
            AttackType strong = entry.getKey();
            AttackType weak = entry.getValue();
            weakAgainst.put(weak, strong);
        }
    }

    @Override
    public void receiveAction(Gotchi gotchi) {

    }

    @Override
    public void resolveRound(Room room) {
        //after round we want to get a clear round
        this.roomMap.put(room, new RoundMessage());
    }

    @Override
    public RoundMessage sendResults(Room room) {
        return null;
    }

    public void startGame(Room room) {
        roomMap.put(room, new RoundMessage());
    }
}
