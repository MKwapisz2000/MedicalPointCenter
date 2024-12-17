package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.DanePacjentaDTO;
import com.przychodnia.przychodnia_aplikacja.model.LekarzDTO;
import com.przychodnia.przychodnia_aplikacja.model.TerminyDTO;
import com.przychodnia.przychodnia_aplikacja.service.GodzinyPracyService;
import com.przychodnia.przychodnia_aplikacja.service.WizytaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class WizytaController {

    private final WizytaService wizytaService;

    public WizytaController(WizytaService wizytaService) {

        this.wizytaService = wizytaService;
    }

    @GetMapping("/pacjent/zapisz_sie")
    public String showSpecjalizacjeForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("PACJENT")) {
            return "redirect:/logowanie";
        }

        return "pacjent/zapisz_sie";
    }

    @PostMapping("/pacjent/zapisz_sie")
    public String processSpecjalizacje(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @RequestParam("specjalizacja") String specjalizacja,
            Model model) {

        try {
            if (specjalizacja.isEmpty()) {
                model.addAttribute("validationError", "Wybierz specjalizację lekrza");
                return "pacjent/zapisz_sie";
            }

            List<LekarzDTO> lekarze = wizytaService.wyborSpecjalizacji(specjalizacja);
            model.addAttribute("lekarze", lekarze);
            return "pacjent/wybor_lekarz";

        } catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());
            if (e.getMessage().contains("lekarzy")) {
                model.addAttribute("validationError", e.getMessage());
            }

            return "pacjent/zapisz_sie";
        }
    }

//-------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/pacjent/wybor_lekarz")
    public String showLekarzeForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("PACJENT")) {
            return "redirect:/logowanie";
        }

        return "pacjent/wybor_lekarz";
    }

    @PostMapping("/pacjent/wybor_lekarz")
    public String processWyborLekarza(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @RequestParam("idLekarza") Long idlekarz,
            Model model) {

        try {
            List<TerminyDTO> terminy = wizytaService.wyborTerminu(idlekarz);

            for (TerminyDTO termin : terminy) {
                List<LocalTime> godzinyDostepne = wizytaService.generujDostepneTerminy(termin.getGodzina_startowa(), termin.getGodzina_koncowa(), termin.getData(), idlekarz);
                termin.setDostepneGodziny(godzinyDostepne);
            }

            model.addAttribute("terminy", terminy);
            model.addAttribute("idlekarz", idlekarz);

            return "pacjent/lekarz_godziny";

        } catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());

            return "pacjent/lekarz_godziny";
        }
    }


//-------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/pacjent/lekarz_godziny")
    public String showTerminyForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("PACJENT")) {
            return "redirect:/logowanie";
        }

        return "pacjent/lekarz_godziny";
    }

    @PostMapping("/pacjent/lekarz_godziny")
    public String processWyborTerminu(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @RequestParam("data") LocalDate data,
            @RequestParam("godzina") LocalTime godzina,
            @RequestParam("idlekarz") Long idlekarz,
            Model model) {

        try {

            wizytaService.saveWizyta(godzina, data, loggedInUser, idlekarz);
            model.addAttribute("successMessage", "Wizyta została zarezerwowana");
            return "pacjent/lekarz_godziny";

        } catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());

            return "pacjent/lekarz_godziny";
        }
    }

}

