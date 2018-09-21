package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.FightServices.FightService;
import com.codecool.pionierzy.gotchiarena.service.FightServices.ParseMessage;
import com.codecool.pionierzy.gotchiarena.service.FightServices.RoundAction;
import com.codecool.pionierzy.gotchiarena.service.FightServices.RoundMessage;
import com.codecool.pionierzy.gotchiarena.service.GotchiServices.GotchiService;
import com.codecool.pionierzy.gotchiarena.service.LobbyServices.LobbyService;
import com.codecool.pionierzy.gotchiarena.service.UserServices.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
public class FightController {

    private final LobbyService lobbyService;
    private final FightService fightService;
    private final UserService userService;
    private final GotchiService gotchiService;

    @Autowired
    public FightController(LobbyService lobbyService,
                           FightService fightService,
                           UserService userService,
                           GotchiService gotchiService ){

        this.lobbyService = lobbyService;
        this.fightService = fightService;
        this.userService = userService;
        this.gotchiService = gotchiService;
    }

    @RequestMapping(value = "/room/{roomId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String room(@PathVariable Long roomId, Principal principal, Model model) {
        Room room = lobbyService.getRoom(roomId);
        model.addAttribute("r", room);

        System.out.println("request to the room/" + roomId);
        if (principal.getName().equals(room.getOwnerName())) {
            System.out.println(room.getOwner().getGotchiList());
            Gotchi gotchi = gotchiService.findById(room.getOwner().getGotchiList().get(0));
            model.addAttribute("gotchi", gotchi);
            model.addAttribute("principal", room.getOwnerName());
        } else if (principal.getName().equals(room.getOpponentName())) {
            Gotchi gotchi = gotchiService.findById(room.getOwner().getGotchiList().get(0));
            model.addAttribute("gotchi", gotchi);
            model.addAttribute("principal", room.getOpponentName());
        }
        return "room";
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

        action = RoundAction.valueOf(parser.getValue());

        Room room = lobbyService.getRoom(Long.parseLong(roomId));
        User user = userService.findByUsername(principal.getName());
        if (fightService.receiveAction(room, user, action)){
            RoundMessage msg = fightService.sendResults(room);
            fightService.getMap().put(room, new RoundMessage());
            return msg;
        }
        return null;
    }

    @MessageMapping("/room/action/{roomId}/start")
    public void startMessage(String message,
                             @DestinationVariable String roomId) throws Exception {
        Room room = lobbyService.getRoom(Long.parseLong(roomId));
        if (fightService.getMap().get(room) == null){
            fightService.startGame(room);
            System.out.println("Connect first user");
        }
        else {
            System.out.println("Connect second user");
        }

    }
    
}
