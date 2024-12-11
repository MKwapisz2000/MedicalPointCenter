package com.przychodnia.przychodnia_aplikacja.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@RequestMapping("/rejestracja_sukces")
@SessionAttributes("rejestrationSuccess")
@Controller
public class RejestracjaSukcesController {

    // Wyświetlanie strony rejestracja zakonczona sukcesem
    @GetMapping("/rejestracja_sukces")
    public String showSukcesPage(HttpSession session) {
        if (session.getAttribute("rejestrationSuccess") == null) {
            // Przekierowanie na stronę rejestracji, jezeli nie zakonczyla sie sukcesem
            return "redirect:/rejestracja";
        }
        // Czyszczenie sesji po wyświetleniu strony
        session.removeAttribute("rejestrationSuccess");
        return "rejestracja_sukces";

    }
}
