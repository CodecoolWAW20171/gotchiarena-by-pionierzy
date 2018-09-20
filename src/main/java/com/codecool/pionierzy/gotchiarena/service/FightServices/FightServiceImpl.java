package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.HashMap;

public class FightServiceImpl implements FightService {

    private HashMap<Room, RoundMessage> roomMap;

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
