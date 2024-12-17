package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.LekarzDTO;
import com.przychodnia.przychodnia_aplikacja.service.HistoriaWizytService;
import com.przychodnia.przychodnia_aplikacja.service.WizytaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class HistoriaWizytController {

    private final HistoriaWizytService historiaWizytService;

    public HistoriaWizytController(HistoriaWizytService historiaWizytService) {
        this.historiaWizytService = historiaWizytService;
    }

    @GetMapping("/pacjent/historia_wizyt")
    public String showHistoriaWizytForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("PACJENT")) {
            return "redirect:/logowanie";
        }

        return "pacjent/historia_wizyt";
    }

        @PostMapping("/pacjent/historia_wizyt")
        public String processHistoriaWizyt (
                @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
                Model model){

            try {

                /*
                model.addAttribute("nadchodzace", nadchodzace);
                model.addAttribute("odbyte", odbyte);*/
                return "pacjent/historia_wizyt";

            } catch (RuntimeException e) {
                model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());


                return "pacjent/historia_wizyt";
            }
        }
    }
