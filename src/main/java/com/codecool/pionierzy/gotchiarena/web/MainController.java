package com.codecool.pionierzy.gotchiarena.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Principal principal) {
        return principal != null ? "redirect:/lobby" : "redirect:/login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/lobby")
    public String lobby() {
        return "lobby";
    }
}
