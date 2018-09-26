package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.dao.UserRepository;
import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.GotchiServices.GotchiService;
import com.codecool.pionierzy.gotchiarena.service.LobbyServices.LobbyService;
import com.codecool.pionierzy.gotchiarena.service.UserServices.UserService;
import com.codecool.pionierzy.gotchiarena.service.message.RoomByIdRequest;
import com.codecool.pionierzy.gotchiarena.service.message.RoomListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.*;

@Controller
public class LobbyController {

    public static final String NOTIFICATION_STREAM = "/queue/notify";
    public static final String ERROR_STREAM = "/queue/error";

    private final LobbyService lobbyService;
    private final GotchiService gotchiService;

    @Autowired
    public LobbyController(LobbyService lobbyService, GotchiService gotchiService) {
        this.lobbyService = lobbyService;
        lobbyService.deleteAllRooms();
        this.gotchiService = gotchiService;
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

    @SendTo("/topic/getGotchi/{userName}")
    @MessageMapping("/room/gotchi/{userName}")
    public ArrayList<Gotchi> getGotchiList(Principal principal, @DestinationVariable String userName){
        System.out.println("|||||||||||>>>>>>> " + userName);
        User user = lobbyService.getUserRepository().findByUsername(principal.getName());
        ArrayList<Long> userGotchiIdList = user.getGotchiList();
        ArrayList<Gotchi> result = new ArrayList<>();
        for (Long id : userGotchiIdList){
            result.add(gotchiService.findById(id));
        }
        System.out.println("ARRAYLIST:");
        System.out.println(result);
        return result;
    }

}
