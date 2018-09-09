package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.service.LobbyService;
import com.codecool.pionierzy.gotchiarena.service.LobbyServiceImpl;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class LobbyController {

    private final LobbyService lobbyService = new LobbyServiceImpl();

    @SubscribeMapping("/rooms")
    public List<Room> getRooms() {
        return lobbyService.getRooms();
    }

    @MessageMapping("/add-room")
    public Room addRoom(Message message, Principal principal) {
        return null;
    }
}
