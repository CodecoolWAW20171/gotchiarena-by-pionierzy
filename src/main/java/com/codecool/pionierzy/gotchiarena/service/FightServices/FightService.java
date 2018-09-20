package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;

public interface FightService {

    public void receiveAction(Room room, User user, RoundAction action);

    public void resolveRound(Room room, RoundMessage roundMessage);

    public RoundMessage sendResults(Room room);

    public void startGame(Room room);
}
