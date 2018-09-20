package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.codecool.pionierzy.gotchiarena.model.Room;

public interface FightService {

    public void receiveAction(Gotchi gotchi);

    public void resolveRound(Room room);

    public RoundMessage sendResults(Room room);
}
