package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.UserService;
import com.codecool.pionierzy.gotchiarena.service.message.RoomByIdRequest;
import com.codecool.pionierzy.gotchiarena.service.message.RoomByNameRequest;
import com.codecool.pionierzy.gotchiarena.service.LobbyService;
import com.codecool.pionierzy.gotchiarena.service.message.RoomByNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class LobbyController {

    private final LobbyService lobbyService;
    private final UserService userService;

    @Autowired
    public LobbyController(LobbyService lobbyService, UserService userService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
        lobbyService.deleteAllRooms();
    }

    @SubscribeMapping("/rooms")
    public List<Room> getRooms() {
        return lobbyService.getRooms();
    }

    @MessageMapping("/add-room")
    public Room addRoom(RoomByNameRequest roomByNameRequest, Principal principal) {
        Room room = lobbyService.addRoom(roomByNameRequest.getName(), principal.getName());
        System.out.println(room);
        return room;
    }

    @MessageMapping("/update-room")
    public Room updateRoom(RoomByIdRequest roomByIdRequest, Principal principal) {
        Room room = lobbyService.joinRoom(roomByIdRequest.getId(), principal.getName());
        System.out.println(room);
        return room;
    }
}
