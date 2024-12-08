package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpecjalisciController {

    // Wyświetlanie strony startowej
    @GetMapping("/specjalisci")
    public String showSpecjalisciPage() {
        return "specjalisci";
    }
}
