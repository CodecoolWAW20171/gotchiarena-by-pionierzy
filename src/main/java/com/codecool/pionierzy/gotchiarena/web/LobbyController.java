package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Player;
import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.service.LobbyService;
import com.codecool.pionierzy.gotchiarena.service.LobbyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
public class LobbyController {

    @Autowired
    private LobbyService lobbyService;

    @SubscribeMapping("/rooms")
    public List<Room> getRooms() {
        List<Room> rooms = new LinkedList<>();
        rooms.add(new Room("Room 1", new Player(1, "Andrzej")));
        rooms.add(new Room("Room 2", new Player(2, "Kolega")));
        rooms.add(new Room("Room Test", new Player(3, "Ziom")));
        return rooms;
    }

    @MessageMapping("/add-room")
    @SendTo("/topic/new-room")
    public Room addRoom(Message message, Principal principal) {
        System.out.println(message);
        return null;
    }
}
