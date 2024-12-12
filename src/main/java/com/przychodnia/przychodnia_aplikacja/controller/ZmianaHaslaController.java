package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.ZmianaHaslaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("loggedInUser")
public class ZmianaHaslaController {

    private final ZmianaHaslaService zmianaHaslaService;
    private final UserRepository userRepository;

    public ZmianaHaslaController(ZmianaHaslaService zmianaHaslaService, UserRepository userRepository) {
        this.zmianaHaslaService = zmianaHaslaService;
        this.userRepository = userRepository;
    }

    // Widok formularza zmiany hasła
    @GetMapping("/zmiana_hasla")
    public String showZmianaHaslaForm(@ModelAttribute("loggedInUser") String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        // Jeśli użytkownik jest zalogowany, załaduj formularz zmiany hasła
        return "zmiana_hasla";  // Ładowanie strony zmiany hasła
    }

    // Obsługa zmiany hasła
    @PostMapping("/zmiana_hasla")
    public String zmianaHasla(
            @RequestParam("noweHaslo") String noweHaslo,
            @RequestParam("powtorzHaslo") String powtorzHaslo,
            @ModelAttribute("loggedInUser") String loggedInUser,
            Model model
    ) {
        if (noweHaslo.isBlank() || powtorzHaslo.isBlank()) {
            model.addAttribute("validationError", "Hasła nie mogą być puste.");
            return "zmiana_hasla";
        }

        if(!(noweHaslo.equals(powtorzHaslo))){
            model.addAttribute("noweHasloError", "Podane hasła nie są takie same!");
            return "zmiana_hasla";
        }

        try {
            // Pobranie ID użytkownika na podstawie e-maila
            Long idUser = userRepository.getIDByEmail(loggedInUser);
            zmianaHaslaService.zmianaHasla(noweHaslo, idUser);
            model.addAttribute("successMessage", "Hasło zostało zmienione.");
            return "zmiana_hasla";

        } catch (Exception e) {

            model.addAttribute("validationError", "Wystąpił błąd podczas zmiany hasła.");
            return "zmiana_hasla";
        }
    }
}
