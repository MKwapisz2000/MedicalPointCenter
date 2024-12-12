package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.DanePacjentaDTO;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.DanePacjentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class EdycjaDanychPacjentController {

    private final DanePacjentaService danePacjentaService;
    private final UserRepository userRepository;


    public EdycjaDanychPacjentController(DanePacjentaService danePacjentaService, UserRepository userRepository) {
        this.danePacjentaService = danePacjentaService;
        this.userRepository = userRepository;
    }

    @GetMapping("/pacjent/edycja_danych")
    public String showEdycjaDanychForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest pacjentem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("PACJENT")) {
            return "redirect:/logowanie";
        }

        try {
            Long idUser = userRepository.getIDByEmail(loggedInUser);
            DanePacjentaDTO danePacjenta = danePacjentaService.showDane(idUser);
            model.addAttribute("danePacjenta", danePacjenta);
            return "pacjent/edycja_danych";

        } catch (Exception e) {

            return "pacjent/dane_osobowe";
        }
    }

    @PostMapping("/pacjent/edycja_danych")
    public String processEdycjaDanych(
            @ModelAttribute("danePacjenta") DanePacjentaDTO danePacjenta,  // Pobierz dane z formularza
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @RequestParam("imie") String imie,
            @RequestParam("nazwisko") String nazwisko,
            @RequestParam("pesel") String pesel,
            @RequestParam("numerTel") String numerTel,
            @RequestParam("email") String email,
            @RequestParam("dataUrodzenia") String dataUrodzenia,
            @RequestParam("miasto") String miasto,
            @RequestParam("ulica") String ulica,
            @RequestParam("nrBudynku") String nrBudynku,
            @RequestParam("nrLokalu") String nrLokalu,
            @RequestParam("kodPocztowy") String kodPocztowy,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest pacjentem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("PACJENT")) {
            return "redirect:/logowanie";
        }

        //Sprawdzenie czy wymagane pola są wypełnione
        if (imie.isEmpty() || nazwisko.isEmpty() || pesel.isEmpty() || numerTel.isEmpty() ||
                email.isEmpty() || dataUrodzenia.isEmpty() || miasto.isEmpty() || ulica.isEmpty() ||
                nrBudynku.isEmpty() || kodPocztowy.isEmpty()) {

            model.addAttribute("validationError", "Wszystkie pola z wyjątkiem numerem lokalu muszą być wypełnione");
            System.out.println("Komunikat o błędzie: " + "nie zapelnione wszystkie pola");
            return "pacjent/edycja_danych";
        }

        try {
            Long idUser = userRepository.getIDByEmail(loggedInUser);
            // Próba edycji danych
            danePacjentaService.edycjaDanych(idUser, imie, nazwisko, pesel, numerTel, email, dataUrodzenia,
                    miasto, ulica, nrBudynku, nrLokalu, kodPocztowy);
            redirectAttributes.addFlashAttribute("successMessage", "Dane zostały zmienione");
            return "redirect:/pacjent/edycja_danych";

        } catch (RuntimeException e) {
            // Obsługa błędów i przekazywanie komunikatów do widoku
            if (e.getMessage().contains("pesel")) {
                model.addAttribute("peselError", e.getMessage());
            } else if (e.getMessage().contains("email")) {
                model.addAttribute("emailError", e.getMessage());
            }
            // Powrót do formularza z komunikatem o błędzie
            return "pacjent/edycja_danych";
        }








    }
}