package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.HistoriaWizytDTO;
import com.przychodnia.przychodnia_aplikacja.model.LekarzDTO;
import com.przychodnia.przychodnia_aplikacja.service.HistoriaWizytService;
import com.przychodnia.przychodnia_aplikacja.service.WizytaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
       // System.out.println(loggedInUser);

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest pacjentem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("PACJENT")) {
            return "redirect:/logowanie";
        }

        try{
            List<HistoriaWizytDTO> historia = historiaWizytService.getHistory(loggedInUser);

            // Jeśli historia jest null, inicjalizujemy jako pustą listę
            if (historia == null) {
                historia = new ArrayList<>();
            }

            // Filtrowanie i sortowanie wizyt nadchodzących (ZAREZERWOWANA)
            List<HistoriaWizytDTO> nadchodzace = historia.stream()
                    .filter(w -> "ZAREZERWOWANA".equalsIgnoreCase(w.getStatus()))
                    .sorted(Comparator.comparing(HistoriaWizytDTO::getData) // Sortowanie po dacie
                            .thenComparing(HistoriaWizytDTO::getGodzina)) // Dodatkowe sortowanie po godzinie
                    .toList();

            // Filtrowanie i sortowanie wizyt odbytych (ODBYTA)
            List<HistoriaWizytDTO> odbyte = historia.stream()
                    .filter(w -> "ODBYTA".equalsIgnoreCase(w.getStatus()))
                    .sorted(Comparator.comparing(HistoriaWizytDTO::getData).reversed() // Sortowanie po dacie od najnowszej
                            .thenComparing(HistoriaWizytDTO::getGodzina, Comparator.reverseOrder())) // Dodatkowe sortowanie po godzinie w odwrotnej kolejności
                    .toList();


            // Przekazanie obu list do modelu
            model.addAttribute("nadchodzace", nadchodzace);
            model.addAttribute("odbyte", odbyte);

            return "pacjent/historia_wizyt";

        }
        catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());
            if (e.getMessage().contains("Brak")) {
                model.addAttribute("validationError", e.getMessage());
            }
            return "pacjent/historia_wizyt";
        }
    }

}
