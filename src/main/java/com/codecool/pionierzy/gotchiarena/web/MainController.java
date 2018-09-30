package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import com.codecool.pionierzy.gotchiarena.model.User;
import com.codecool.pionierzy.gotchiarena.service.GotchiServices.GotchiService;
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
import java.util.ArrayList;
import java.util.LinkedList;

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
    public String lobby(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        System.out.println(user.getGotchiList());
        return "lobby";
    }

    @GetMapping("/create_gotchi")
    public String create_gotchi(Model model, Principal principal) {
        Gotchi gotchi = new Gotchi();
        model.addAttribute("gotchi", gotchi);
        User user = userService.findByUsername(principal.getName());
        return "create_gotchi";
    }

    @GetMapping("/user/{userName}")
    public String userPage(Model model, @PathVariable String userName){
        User user = userService.findByUsername(userName);
        model.addAttribute("userName", userName);
        LinkedList<Gotchi> gotchiList = new LinkedList();
        for (long id : user.getGotchiList()){
            Gotchi gotchi = gotchiService.findById(id);
            gotchiList.add(gotchi);
        }
        model.addAttribute("list", gotchiList);
        return "userPage";
    }

    @GetMapping("/user")
    public String yourPage(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("userName", user.getUsername());
        LinkedList<Gotchi> gotchiList = new LinkedList();
        for (long id : user.getGotchiList()){
            Gotchi gotchi = gotchiService.findById(id);
            gotchiList.add(gotchi);
        }
        model.addAttribute("list", gotchiList);
        return "userPage";
    }

    @RequestMapping(value = "/create_gotchi", method = RequestMethod.POST)
    public String createNewGotchi(@Valid Gotchi gotchi, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        gotchi.setUserId(user.getId());
        System.out.println(gotchi.getUserId());
        gotchiService.save(gotchi);
        System.out.println(user.getGotchiList());
        ArrayList<Long> tempList = user.getGotchiList();
        tempList.add(gotchi.getId());
        user.setGotchiList(tempList);
        System.out.println(user.getGotchiList());
        userService.update(user);
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
