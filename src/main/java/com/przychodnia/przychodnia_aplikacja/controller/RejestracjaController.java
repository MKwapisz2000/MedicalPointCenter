package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.service.RejestracjaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rejestracja")
public class RejestracjaController {

    private final RejestracjaService rejestracjaService;

    public RejestracjaController(RejestracjaService rejestracjaService) {
        this.rejestracjaService = rejestracjaService;
    }

    @PostMapping
    public String registerUser(
            @RequestParam String imie,
            @RequestParam String nazwisko,
            @RequestParam String pesel,
            @RequestParam String numerTel,
            @RequestParam String email,
            @RequestParam String haslo,
            @RequestParam String dataUrodzenia) {
        try {
            // Wywołanie serwisu rejestracji
            rejestracjaService.rejestracjaNewPatient(imie, nazwisko, pesel, numerTel, email, haslo, dataUrodzenia);

            // Przekierowanie na stronę sukcesu
            return "redirect:/success.html";
        } catch (Exception e) {
            // Przekierowanie na stronę błędu
            return "redirect:/error.html";
        }
    }
}
