package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.GotchiServices.GotchiService;
import com.codecool.pionierzy.gotchiarena.service.UserServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {

    private final UserService userService;
    private final GotchiService gotchiService;

    @Autowired
    public MainController(UserService userService, GotchiService gotchiService) {

        this.userService = userService;
        this.gotchiService = gotchiService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/lobby";
    }

    @RequestMapping("/login")
    public String login(Principal principal, WebRequest request, Model model) {
//        addTestUser("user");
//        addTestUser("user2");
        User user = new User();
        model.addAttribute("user", user);
        return principal != null ? "redirect:/lobby" : "login";
    }

    @RequestMapping("/lobby")
    public String lobby() {
        return "lobby";
    }

    @GetMapping("/create_gotchi")
    public String create_gotchi(Model model) {
        Gotchi gotchi = new Gotchi();
        model.addAttribute("gotchi", gotchi);
        return "create_gotchi";
    }

    @RequestMapping(value = "/create_gotchi", method = RequestMethod.POST)
    public String createNewGotchi(@Valid Gotchi gotchi) {
        System.out.println(gotchi);
        gotchiService.save(gotchi);
        return "redirect:/lobby";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createNewUser(@Valid User user) {
        User userExists = userService.findByUsername(user.getUsername());
        if (userExists == null) {
            userService.save(user);
        }
        return "redirect:/lobby";
    }

    private void addTestUser(String username) {
        User user = new User(username);
        user.setPassword("");
        userService.save(user);
    }
}
