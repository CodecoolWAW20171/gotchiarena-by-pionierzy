package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;

import java.util.HashMap;

public interface FightService {

    public boolean receiveAction(Room room, User user, RoundAction action);

    public void resolveRound(Room room, RoundMessage roundMessage);

    public RoundMessage sendResults(Room room);

    public void startGame(Room room);

    public HashMap getMap();
}
