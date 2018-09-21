package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.service.LobbyServices.LobbyService;
import com.codecool.pionierzy.gotchiarena.service.message.RoomByIdRequest;
import com.codecool.pionierzy.gotchiarena.service.message.RoomListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class LobbyController {

    public static final String NOTIFICATION_STREAM = "/queue/notify";
    public static final String ERROR_STREAM = "/queue/error";

    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
        lobbyService.deleteAllRooms();
    }

    @SubscribeMapping("/rooms")
    public RoomListDto getRooms(Principal principal) {
        List<Room> rooms = lobbyService.getRooms();
        Long userRoomId = lobbyService.getRoomIdForUser(principal.getName());
        return new RoomListDto(userRoomId, rooms);
    }

    @MessageMapping("/add-room")
    public Room addRoom(Principal principal) {
        Room room = lobbyService.addRoom(principal.getName());
        return room;
    }

    @MessageMapping("/update-room")
    public Room updateRoom(RoomByIdRequest roomByIdRequest, Principal principal) {
        Room room = null;
        switch (roomByIdRequest.getAction()) {
            case JOIN:
                room = lobbyService.joinRoom(roomByIdRequest.getId(), principal.getName());
                break;
            case LEAVE:
                room = lobbyService.leaveRoom(roomByIdRequest.getId(), principal.getName());
                break;
        }
        return room;
    }

    @MessageMapping("/delete-room")
    public Long deleteRoom(RoomByIdRequest roomByIdRequest, Principal principal) {
        return lobbyService.deleteRoom(roomByIdRequest.getId(), principal.getName());
    }

    @RequestMapping(value = "/room/{roomId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String room(@PathVariable Long roomId, Principal principal, Model model) {
        Room room = lobbyService.getRoom(roomId);
        model.addAttribute("r", room);
        System.out.println("request to the room/" + roomId);
        if (principal.getName().equals(room.getOwnerName())) {
            model.addAttribute("principal", room.getOwnerName());
        } else if (principal.getName().equals(room.getOpponentName())) {
            model.addAttribute("principal", room.getOpponentName());
        }
        return "room";
    }
}
