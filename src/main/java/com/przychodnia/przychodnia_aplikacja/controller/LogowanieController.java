package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogowanieController {

    // Wyświetlanie strony startowej
    @GetMapping("/logowanie")
    public String showLogowaniePage() {
        return "logowanie";
    }
}
