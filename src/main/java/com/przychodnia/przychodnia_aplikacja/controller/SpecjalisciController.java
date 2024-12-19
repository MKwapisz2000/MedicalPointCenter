package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.HistoriaWizytDTO;
import com.przychodnia.przychodnia_aplikacja.model.LekarzDTO;
import com.przychodnia.przychodnia_aplikacja.model.SpecjalisciDTO;
import com.przychodnia.przychodnia_aplikacja.service.HistoriaWizytService;
import com.przychodnia.przychodnia_aplikacja.service.SpecjalisciService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class SpecjalisciController {

    private final SpecjalisciService specjalisciService;

    public SpecjalisciController(SpecjalisciService specjalisciService) {
        this.specjalisciService = specjalisciService;
    }


    // Wyświetlanie strony startowej
    @GetMapping("/specjalisci")
    public String showSpecjalisciPage(Model model) {
        try {
            List<SpecjalisciDTO> lekarze = specjalisciService.showSpecjalisci();

            //Przekazanie  listy do modelu
            model.addAttribute("lekarze", lekarze);

            return "specjalisci";

        } catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());

            return "specjalisci";
        }

    }
}
