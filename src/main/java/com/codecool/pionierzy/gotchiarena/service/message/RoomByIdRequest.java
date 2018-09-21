package com.codecool.pionierzy.gotchiarena.service.message;

import com.codecool.pionierzy.gotchiarena.service.LobbyServices.RoomAction;

public class RoomByIdRequest {
    private Long id;

    private RoomAction action;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomAction getAction() {
        return action;
    }

    public void setAction(RoomAction action) {
        this.action = action;
    }
}
