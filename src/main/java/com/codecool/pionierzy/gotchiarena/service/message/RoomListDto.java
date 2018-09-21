package com.codecool.pionierzy.gotchiarena.service.message;

import com.codecool.pionierzy.gotchiarena.model.Room;

import java.util.List;

public class RoomListDto {
    private Long userRoomId;
    private List<Room> rooms;

    public RoomListDto(Long userRoomId, List<Room> rooms) {
        this.userRoomId = userRoomId;
        this.rooms = rooms;
    }

    public Long getUserRoomId() {
        return userRoomId;
    }

    public void setUserRoomId(Long userRoomId) {
        this.userRoomId = userRoomId;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
