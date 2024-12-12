package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/pacjent")
@SessionAttributes({"loggedInUser", "userRole"})
public class PacjentController {

    @GetMapping("/dashboard")
    public String pacjentDashboard(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @SessionAttribute(value = "userRole", required = false) String userRole,
            Model model) {
        // Sprawdzanie, czy użytkownik jest zalogowany
        if (loggedInUser == null || userRole == null) {
            return "redirect:/logowanie";
        }
        // Sprawdzanie, czy użytkownik ma rolę PACJENT
        if (!"PACJENT".equalsIgnoreCase(userRole)) {
            return "redirect:/logowanie";
        }

        return "pacjent/dashboard"; // Załadowanie widoku pacjent/dashboard.html
    }
}
