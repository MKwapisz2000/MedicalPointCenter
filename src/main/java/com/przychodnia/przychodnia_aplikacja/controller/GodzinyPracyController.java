package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.service.GodzinyPracyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class GodzinyPracyController {

    private final GodzinyPracyService godzinyPracyService;

    public GodzinyPracyController(GodzinyPracyService godzinyPracyService) {

        this.godzinyPracyService = godzinyPracyService;
    }


    @GetMapping("/lekarz/godziny_pracy")
    public String showGodzinyPracyForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("LEKARZ")) {
            return "redirect:/logowanie";
        }

        return "lekarz/godziny_pracy";
    }

    @PostMapping("/lekarz/godziny_pracy")
    public String processGodzinyPracy(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @RequestParam List<String> data,
            @RequestParam List<String> godzina_startowa,
            @RequestParam List<String> godzina_koncowa,
            Model model) {

        try{
            if (data.isEmpty() || godzina_startowa.isEmpty() || godzina_koncowa.isEmpty()) {
                model.addAttribute("validationError", "Wszystkie pola muszą być wypełnione.");
                return "lekarz/godziny_pracy";
            }

            godzinyPracyService.zapiszGodziny(data, godzina_startowa, godzina_koncowa, loggedInUser);
            model.addAttribute("successMessage", "Godziny pracy zostały zapisane.");

        }
        catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());
            if (e.getMessage().contains("Godzina")) {
                model.addAttribute("validationError", e.getMessage());
            }
        }

        return "lekarz/godziny_pracy";
    }
}
