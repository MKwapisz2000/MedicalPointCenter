package com.przychodnia.przychodnia_aplikacja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"loggedInUser", "userRole"})
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @SessionAttribute(value = "userRole", required = false) String userRole,
            Model model) {
        // Sprawdzanie, czy użytkownik jest zalogowany
        if (loggedInUser == null || userRole == null) {
            return "redirect:/logowanie";
        }
        // Sprawdzanie, czy użytkownik ma rolę ADMIN
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            return "redirect:/logowanie";
        }

        return "admin/dashboard";
    }
}
