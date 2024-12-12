package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.DanePacjentaDTO;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.DanePacjentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class DanePacjentaController {

    private final DanePacjentaService danePacjentaService;
    private final UserRepository userRepository;

    public DanePacjentaController(DanePacjentaService danePacjentaService, UserRepository userRepository) {
        this.danePacjentaService = danePacjentaService;
        this.userRepository = userRepository;
    }


    @GetMapping("/pacjent/dane_osobowe")
    public String showDaneOsoboweForm(@SessionAttribute(value ="loggedInUser", required = false) String loggedInUser, Model model) {
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
            // Pobranie ID użytkownika na podstawie e-maila
            Long idUser = userRepository.getIDByEmail(loggedInUser);
            DanePacjentaDTO danePacjenta = danePacjentaService.showDane(idUser);
            model.addAttribute("danePacjenta", danePacjenta);
            return "pacjent/dane_osobowe";

        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());

            return "pacjent/dane_osobowe";
        }

    }
}
