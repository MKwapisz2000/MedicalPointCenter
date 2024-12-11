package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ONasController {

    // Wyświetlanie strony startowej
    @GetMapping("/onas")
    public String showONasPage() {
        return "onas";
    }
}
