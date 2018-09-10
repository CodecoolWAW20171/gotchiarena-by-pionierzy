package com.codecool.pionierzy.gotchiarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DisplaySomeShitController {
    @GetMapping("/shit")
    public String shit(Model model) {
        model.addAttribute("whatisspring", "Spring is shit!");
        return "shit";
    }
}
