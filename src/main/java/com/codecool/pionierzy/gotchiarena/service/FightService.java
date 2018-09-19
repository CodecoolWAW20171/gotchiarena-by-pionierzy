package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;

public interface FightService {

    public void receiveAction(Gotchi gotchi);

    public void resolveRound();

    public RoundMessage sendResults();
}
