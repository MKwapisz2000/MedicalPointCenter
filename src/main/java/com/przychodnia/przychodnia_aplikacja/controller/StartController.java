package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    // Wyświetlanie strony startowej
    @GetMapping("/start")
    public String showStartPage() {
        return "start";
    }
}
