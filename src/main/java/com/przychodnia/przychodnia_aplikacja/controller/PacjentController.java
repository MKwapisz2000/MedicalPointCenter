package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/pacjent")
@SessionAttributes("loggedInUser")
public class PacjentController {

    @GetMapping("/dashboard")
    public String pacjentDashboard(Model model) {
        // Sprawdzanie, czy użytkownik jest zalogowany
        if (!model.containsAttribute("loggedInUser")) {
            return "redirect:/logowanie"; // Przekierowanie na stronę logowania, jeśli użytkownik nie jest zalogowany
        }
        return "pacjent/dashboard"; // Załadowanie widoku pacjent/dashboard.html
    }
}
