package com.codecool.pionierzy.gotchiarena.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FightController {

    @MessageMapping("/attack")
    @SendTo("/topic/message")
    public String logs(String message) throws Exception {

        return message;
    }
}
