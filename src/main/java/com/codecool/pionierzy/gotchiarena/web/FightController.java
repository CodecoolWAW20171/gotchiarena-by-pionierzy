package com.codecool.pionierzy.gotchiarena.web;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FightController {

    @MessageMapping("/room/action/{roomId}")
    @SendTo("/topic/message/{roomId}")
    public String logs(String message, @DestinationVariable String roomId) throws Exception {
        System.out.println("-----------------");
        System.out.println(roomId);
        return message;
    }
}
