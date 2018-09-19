package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.RoomRequest;
import com.codecool.pionierzy.gotchiarena.service.LobbyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.codecool.pionierzy.gotchiarena.service.LobbyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.List;

@Controller
public class LobbyController {

    private final LobbyService lobbyService;
    private final ObjectMapper objectMapper;

    @Autowired
    public LobbyController(LobbyService lobbyService, ObjectMapper objectMapper) {
        this.lobbyService = lobbyService;
        this.objectMapper = objectMapper;
    }

    @SubscribeMapping("/rooms")
    public List<Room> getRooms() {
        return lobbyService.getRooms();
    }

    @MessageMapping("/add-room")
    public Room addRoom(RoomRequest roomRequest, Principal principal) {

        Room room = lobbyService.addRoom(roomRequest.getName(), principal.getName());
        System.out.println(room);
        return room;
    }

    @RequestMapping(value = "/room/{roomId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String room(@PathVariable String roomId, Principal principal, Model model,
                       @RequestParam(value="test",required=false) String test){
        Room room = lobbyService.getRoomsMap().get(roomId);
        model.addAttribute("r", room);
        System.out.println("request at room/id");
        model.addAttribute("test", test);
        return "room";
    }

//    TEST: creating room and user inside
    @GetMapping("/testing")
    public String createRoom(){
        lobbyService.addRoom("GO", "Andrzej");
        return "redirect:/login";
    }
}
