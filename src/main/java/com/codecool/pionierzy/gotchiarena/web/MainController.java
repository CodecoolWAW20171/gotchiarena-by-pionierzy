package com.codecool.pionierzy.gotchiarena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "redirect:/lobby";
    }

    @RequestMapping("/login")
    public String login(Principal principal) {
        return principal != null ? "redirect:/lobby" : "login";
    }

    @RequestMapping("/lobby")
    public String lobby() {
        return "lobby";
    }
}
