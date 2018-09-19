package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;

public class FightServiceImpl implements FightService {

    private RoundMessage roundMessage;

    @Override
    public void receiveAction(Gotchi gotchi) {

    }

    @Override
    public void resolveRound() {

        //after round we want to get a clear round
        this.roundMessage = new RoundMessage();
    }

    @Override
    public RoundMessage sendResults() {
        return null;
    }
}
