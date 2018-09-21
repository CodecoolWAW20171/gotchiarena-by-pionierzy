package com.codecool.pionierzy.gotchiarena.service.LobbyServices;

import com.codecool.pionierzy.gotchiarena.dao.RoomRepository;
import com.codecool.pionierzy.gotchiarena.dao.UserRepository;
import com.codecool.pionierzy.gotchiarena.model.Room;
import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.message.Notification;
import com.codecool.pionierzy.gotchiarena.web.LobbyController;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class LobbyServiceImpl implements LobbyService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public LobbyServiceImpl(UserRepository userRepository, RoomRepository roomRepository, SimpMessageSendingOperations messagingTemplate) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(Long id) {
        return roomRepository.findRoomById(id);
    }

    @Override
    public Long getRoomIdForUser(String username) {
        Room room = roomRepository.findRoomByOwnerUsernameOrOpponentUsername(username, username);
        return room != null ? room.getId() : null;
    }

    @Override
    public Room addRoom(String ownerName) {
        if (isUserInRoom(ownerName, "You are already in a room.")) return null;
        User owner = userRepository.findByUsername(ownerName);
        Room room = new Room(owner);
        roomRepository.save(room);
        sendRoomIdUpdateToUser(ownerName, room.getId(), "Your room has been created.");
        return room;
    }

    @Override
    public Room leaveRoom(Long id, String username) {
        Room room = roomRepository.findRoomById(id);
        if (room != null && room.getOpponentName().equals(username)){
            sendRoomIdUpdateToUser(username, null, "You have left the room.");
            room.setOpponent(null);
            System.out.println(room.getOpponentName());
            roomRepository.save(room);
            System.out.println(room);
            return room;
        }
        sendErrorToUser(username, new Notification("You are not in this room."));
        return null;
    }

    @Override
    public Long deleteRoom(Long id, String ownerName) {
        Room room = roomRepository.findRoomByOwnerUsername(ownerName);
        if (room != null && room.getOwnerName().equals(ownerName)) {
            roomRepository.delete(room);
            sendRoomIdUpdateToUser(ownerName, null, "Room deleted.");
            if (room.getOpponentName() != null) {
                sendRoomIdUpdateToUser(room.getOpponentName(), null, "Room deleted.");
            }
            return id;
        }
        sendErrorToUser(ownerName, new Notification("You are not the owner of this room."));
        return null;
    }

    @Override
    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }

    @Override
    public Room joinRoom(Long id, String username) {
        if (isUserInRoom(username, "You are already in a room.")) return null;
        Room room = roomRepository.findRoomById(id);
        User user = userRepository.findByUsername(username);
        if (room == null || user == null || !room.setOpponent(user)) {
            sendErrorToUser(username, new Notification("Unable to join."));
            return null;
        }
        roomRepository.save(room);
        sendRoomIdUpdateToUser(username, room.getId(),
                "You have joined " + room.getOwnerName() + "'s room.");
        return room;
    }

    private boolean isUserInRoom(String username, String msgOnFalse) {
        if (roomRepository
                .findRoomByOwnerUsernameOrOpponentUsername(username, username) != null) {
            sendErrorToUser(username, new Notification(msgOnFalse));
            return true;
        }
        return false;
    }

    private void sendMsgToUser(String username, String destination, Notification notification) {
        Map<String, Object> map = new HashMap<>();
        map.put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        messagingTemplate.convertAndSendToUser(username, destination, notification, map);
    }

    private void sendNotificationToUser(String username, Notification notification) {
        sendMsgToUser(username, LobbyController.NOTIFICATION_STREAM, notification);
    }

    private void sendErrorToUser(String username, Notification notification) {
        sendMsgToUser(username, LobbyController.ERROR_STREAM, notification);
    }

    private void sendRoomIdUpdateToUser(String username, Long roomId, String msg) {
        Notification notification = new Notification(msg);
        notification.setUserRoomId(roomId);
        sendNotificationToUser(username, notification);
    }
}
