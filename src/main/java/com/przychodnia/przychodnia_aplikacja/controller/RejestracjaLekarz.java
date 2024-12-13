package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.DanePacjentaService;
import com.przychodnia.przychodnia_aplikacja.service.RejestracjaLekarzService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class RejestracjaLekarz {

    private final RejestracjaLekarzService rejestracjaLekarzService;
    private final UserRepository userRepository;

    public RejestracjaLekarz(RejestracjaLekarzService rejestracjaLekarzService, UserRepository userRepository) {
        this.rejestracjaLekarzService = rejestracjaLekarzService;
        this.userRepository = userRepository;
    }


    @GetMapping("/admin/rejestracja_lekarz")
    public String showRejestracjaLekarzForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("ADMIN")) {
            return "redirect:/logowanie";
        }

        return "admin/rejestracja_lekarz";
    }

        @PostMapping("/admin/rejestracja_lekarz")
        public String processRejestracja(
                @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
                @RequestParam("imie") String imie,
                @RequestParam("nazwisko") String nazwisko,
                @RequestParam("email") String email,
                @RequestParam("haslo") String haslo,
                @RequestParam List<String> specjalizacje,
                Model model) {

            //Sprawdzenie czy wymagane pola są wypełnione
            if (imie.isEmpty() || nazwisko.isEmpty() || email.isEmpty() || haslo.isEmpty() ||
                    specjalizacje.isEmpty() ) {

                model.addAttribute("validationError", "Wszystkie pola muszą być wypełnione");
                return "admin/rejestracja_lekarz";
            }

        try{
            rejestracjaLekarzService.rejestracjaNewLekarz(imie, nazwisko, email, haslo, specjalizacje);
            model.addAttribute("successMessage", "Rejestracja przebiegła prawidłowo");
            return "admin/rejestracja_lekarz";
        }
        catch (RuntimeException e) {
            // Obsługa błędów i przekazywanie komunikatów do widoku
            if (e.getMessage().contains("email")) {
                model.addAttribute("emailError", e.getMessage());
            }
            // Powrót do formularza z komunikatem o błędzie
            return "admin/rejestracja_lekarz";
        }
    }
}
