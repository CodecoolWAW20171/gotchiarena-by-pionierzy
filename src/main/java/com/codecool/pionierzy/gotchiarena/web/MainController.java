package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.UserServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/lobby";
    }

    @RequestMapping("/login")
    public String login(Principal principal, WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return principal != null ? "redirect:/lobby" : "login";
    }

    @RequestMapping("/lobby")
    public String lobby() {
        return "lobby";
    }

    @RequestMapping("/create_gotchi")
    public String create_gotchi() {
        return "create_gotchi";
    }




    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createNewUser(@Valid User user) {
        User userExists = userService.findByUsername(user.getUsername());
        if (userExists == null) {
            userService.save(user);
        }
        return "redirect:/lobby";
    }
}
