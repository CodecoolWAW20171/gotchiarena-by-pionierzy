package com.codecool.pionierzy.gotchiarena.service.message;

public class RoomByIdRequest {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Joining room (id): " + id;
    }
}
