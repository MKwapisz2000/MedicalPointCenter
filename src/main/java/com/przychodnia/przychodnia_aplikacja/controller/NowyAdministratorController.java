package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.service.NowyAdministratorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NowyAdministratorController {

    private final NowyAdministratorService nowyadministratorService;

    public NowyAdministratorController(NowyAdministratorService nowyadministratorService) {
        this.nowyadministratorService = nowyadministratorService;
    }

    // Wyświetlanie formularza rejestracji
    @GetMapping("/admin/addAdmin")
    public String showRejestracjaForm() {
        return "admin/addAdmin"; // Ładuje plik rejestracja.html z folderu templates
    }

    // Obsługuje przesyłanie formularza rejestracji
    @PostMapping("/admin/addAdmin")
    public String processRejestracja(
            @RequestParam("imie") String imie,
            @RequestParam("nazwisko") String nazwisko,
            @RequestParam("email") String email,
            @RequestParam("haslo") String haslo,
            Model model
    ) {
        try {
            // Próba rejestracji
            nowyadministratorService.newAdministrator(imie, nazwisko, email, haslo);
            model.addAttribute("successMessage", "Rejestracja zakończona sukcesem!");
            return "admin/addAdmin"; // Pozostajemy na stronie rejestracji, z komunikatem o sukcesie

        } catch (RuntimeException e) {
            // Obsługa błędów i przekazywanie komunikatów do widoku
            if (e.getMessage().contains("email")) {
                model.addAttribute("emailError", e.getMessage());
            }

            return "admin/addAdmin"; // Wróć do formularza z komunikatem o błędzie
        }
    }
}