package com.przychodnia.przychodnia_aplikacja.controller;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.LogowanieService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class LogowanieController {

    private final LogowanieService logowanieService;
    private final UserRepository userRepository;

    public LogowanieController(LogowanieService logowanieService, UserRepository userRepository) {
        this.logowanieService = logowanieService;
        this.userRepository = userRepository;
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
            model.addAttribute("loggedInUser", email);
            model.addAttribute("userRole", userRepository.getTypByEmail(email));
            System.out.println("userRole w sesji: " + model.getAttribute("userRole"));


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

        String userRole = (String) model.getAttribute("userRole");
        if ("ADMIN".equalsIgnoreCase(userRole)) {
            return "redirect:/admin/dashboard"; // Przekierowanie do panelu administratora
        } else if ("PACJENT".equalsIgnoreCase(userRole)) {
            return "redirect:/pacjent/dashboard"; // Przekierowanie do panelu pacjenta
        } else if ("LEKARZ".equalsIgnoreCase(userRole)) {
            return "redirect:/lekarz/dashboard"; // Przekierowanie do panelu lekarza
        }

        return "redirect:/logowanie";
    }
}
