package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.service.RejestracjaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RejestracjaController {

    private final RejestracjaService rejestracjaService;

    public RejestracjaController(RejestracjaService rejestracjaService) {
        this.rejestracjaService = rejestracjaService;
    }

    // Wyświetlanie formularza rejestracji
    @GetMapping("/rejestracja")
    public String showRejestracjaForm() {
        return "rejestracja"; // Ładuje plik rejestracja.html z folderu templates
    }

    // Obsługuje przesyłanie formularza rejestracji
    @PostMapping("/rejestracja")
    public String processRejestracja(
            @RequestParam("imie") String imie,
            @RequestParam("nazwisko") String nazwisko,
            @RequestParam("pesel") String pesel,
            @RequestParam("numerTel") String numerTel,
            @RequestParam("email") String email,
            @RequestParam("haslo") String haslo,
            @RequestParam("dataUrodzenia") String dataUrodzenia,
            @RequestParam("miasto") String miasto,
            @RequestParam("ulica") String ulica,
            @RequestParam("nrBudynku") String nrBudynku,
            @RequestParam("nrLokalu") String nrLokalu,
            @RequestParam("kodPocztowy") String kodPocztowy,
            @RequestParam(value = "regulamin", defaultValue = "false") boolean regulamin, // Domyślnie nie zaznaczone
            Model model
    ) {
        try {
            // Sprawdzanie, czy checkbox regulaminu jest zaznaczony
            if (!regulamin) {
                model.addAttribute("regulaminError", "Musisz zaakceptować regulamin, aby kontynuować.");
                return "rejestracja"; // Wróć do formularza, gdy regulamin nie jest zaakceptowany
            }

            // Próba rejestracji
            rejestracjaService.rejestracjaNewPatient(imie, nazwisko, pesel, numerTel, email, haslo, dataUrodzenia, miasto, ulica, nrBudynku, nrLokalu, kodPocztowy);
            model.addAttribute("successMessage", "Rejestracja zakończona sukcesem!");

            return "rejestracja"; // Pozostajemy na stronie rejestracji, z komunikatem o sukcesie
        } catch (RuntimeException e) {
            // Obsługa błędów i przekazywanie komunikatów do widoku
            if (e.getMessage().contains("Pesel")) {
                model.addAttribute("peselError", e.getMessage());
            } else if (e.getMessage().contains("Email")) {
                model.addAttribute("emailError", e.getMessage());
            }

            return "rejestracja"; // Wróć do formularza z komunikatem o błędzie
        }
    }
}
