package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "redirect:/lobby";
    }

    @RequestMapping("/login")
    public String login(Principal principal, WebRequest request, Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return principal != null ? "redirect:/lobby" : "login";
    }

    @RequestMapping("/lobby")
    public String lobby() {
        return "lobby";
    }
}
