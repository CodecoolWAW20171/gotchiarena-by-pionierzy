package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.FightServices.FightService;
import com.codecool.pionierzy.gotchiarena.service.FightServices.ParseMessage;
import com.codecool.pionierzy.gotchiarena.service.FightServices.RoundAction;
import com.codecool.pionierzy.gotchiarena.service.FightServices.RoundMessage;
import com.codecool.pionierzy.gotchiarena.service.LobbyServices.LobbyService;
import com.codecool.pionierzy.gotchiarena.service.UserServices.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class FightController {

    private final LobbyService lobbyService;
    private final FightService fightService;
    private final UserService userService;

    @Autowired
    public FightController(LobbyService lobbyService, FightService fightService, UserService userService){
        this.lobbyService = lobbyService;
        this.fightService = fightService;
        this.userService = userService;
    }

    @MessageMapping("/room/action/{roomId}")
    @SendTo("/topic/message/{roomId}")
    public RoundMessage logs(String message,
                             Principal principal,
                             @DestinationVariable String roomId) throws Exception {

        RoundAction action = null;

        ParseMessage parser = new ObjectMapper()
                .readerFor(ParseMessage.class)
                .readValue(message);

        switch (parser.getValue()){
            case "attack1":
                action = RoundAction.PRIMARY_ATTACK;
                System.out.println("1");
                break;
            case "attack2":
                action = RoundAction.SECONDARY_ATTACK;
                System.out.println("2");
                break;
            case "defend":
                action = RoundAction.DEFEND;
                System.out.println("3");
                break;
            case "evade":
                action = RoundAction.EVADE;
                System.out.println("4");
                break;
            default:
                action = RoundAction.PRIMARY_ATTACK;
                System.out.println("WRONG");

        }

        Room room = lobbyService.getOneRoom(roomId);
        User user = userService.findByUsername(principal.getName());
        fightService.startGame(room);
        fightService.receiveAction(room, user, action);
        return fightService.sendResults(room);
    }
    
}
