package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KontaktController {

    // Wyświetlanie strony startowej
    @GetMapping("/kontakt")
    public String showKontaktPage() {
        return "kontakt";
    }
}
