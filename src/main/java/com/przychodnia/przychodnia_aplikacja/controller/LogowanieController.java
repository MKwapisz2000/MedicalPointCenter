package com.przychodnia.przychodnia_aplikacja.controller;
import com.przychodnia.przychodnia_aplikacja.service.LogowanieService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("loggedInUser")
public class LogowanieController {

    private final LogowanieService logowanieService;

    public LogowanieController(LogowanieService logowanieService) {
        this.logowanieService = logowanieService;
    }

    // Wyświetlanie formularza logowania
    @GetMapping("/logowanie")
    public String showLogowanieForm() {
        // Ładowanie pliku logowanie.html z folderu templates
        return "logowanie";
    }

    // Obsługuje przesyłanie formularza logowania
    @PostMapping("/logowanie")
    public String processLogowanie(
            @RequestParam("email") String email,
            @RequestParam("haslo") String haslo,
            Model model
    ) {
        if (email.isBlank() || haslo.isBlank()) {
            model.addAttribute("validationError", "Email i hasło nie mogą być puste.");
            return "logowanie";
        }

        try {
            // Próba logowania
            String redirectPage = logowanieService.logowanieUser(email, haslo);
            model.addAttribute("successMessage", "Logowanie zakończone sukcesem!");
            model.addAttribute("loggedInUser", email); // Przechowywanie w sesji
            // Pozostajemy na stronie logowania, z komunikatem o sukcesie
            return redirectPage;

        } catch (RuntimeException e) {
            System.out.println("Błąd: " + e.getMessage());

            // Obsługa błędów i przekazywanie komunikatów do widoku
            if (e.getMessage().contains("email")) {
                model.addAttribute("emailError", e.getMessage());
            }
            else if (e.getMessage().contains("hasło")) {
                model.addAttribute("hasloError", e.getMessage());
            }
            else if (e.getMessage().contains("nieaktywny")) {
                model.addAttribute("statusError", e.getMessage());
            }

            // Wróć do formularza z komunikatem o błędzie
            return "logowanie";
        }

    }

    // Obsługa wylogowania
    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Usuwanie danych z sesji
        return "redirect:/start"; // Przekierowanie na stronę logowania
    }

    // Przykład strony chronionej, dostępnej tylko dla zalogowanych użytkowników
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        if (!model.containsAttribute("loggedInUser")) {
            return "redirect:/logowanie"; // Przekierowanie na logowanie, jeśli użytkownik nie jest zalogowany
        }
        return "/dashboard"; // Załadowanie strony dashboard.html
    }





}
