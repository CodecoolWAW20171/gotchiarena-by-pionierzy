package com.codecool.pionierzy.gotchiarena.web;

import com.codecool.pionierzy.gotchiarena.service.GotchiServices.GotchiService;
import com.codecool.pionierzy.gotchiarena.service.UserServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserDataController {

    private final UserService userService;
    private final GotchiService gotchiService;

    @Autowired
    public UserDataController(UserService userService, GotchiService gotchiService) {
        this.userService = userService;
        this.gotchiService = gotchiService;
    }

//    @RequestMapping("/user/you")
//    public String user(){
//
//    }

}
