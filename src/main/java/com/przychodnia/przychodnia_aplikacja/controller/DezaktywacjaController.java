package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.DezaktywacjaService;
import com.przychodnia.przychodnia_aplikacja.service.RejestracjaLekarzService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class DezaktywacjaController {

    private final DezaktywacjaService dezaktywacjaService;

    public DezaktywacjaController(DezaktywacjaService dezaktywacjaService) {
        this.dezaktywacjaService = dezaktywacjaService;

    }

    @GetMapping("/admin/dezaktywacja")
    public String showDezaktywacjaForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("ADMIN")) {
            return "redirect:/logowanie";
        }

        return "admin/dezaktywacja";
    }

    @PostMapping("/admin/dezaktywacja")
    public String processDezaktywacja(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @RequestParam("email") String email,
            @RequestParam ("action") String action,
            Model model) {

        //Sprawdzenie czy wymagane pola są wypełnione
        if (email.isEmpty()) {
            model.addAttribute("validationError", "Pole email musi być wypełnione");
            return "admin/dezaktywacja";
        }

        try{
            dezaktywacjaService.dezaktywacjaUser(email, action);
            if(action.equals("AKTYWNY")) {
                model.addAttribute("successMessage", "Aktywacja przebiegła prawidłowo");
            }
            else if(action.equals("NIEAKTYWNY")){
                model.addAttribute("successMessage", "Dezaktywacja przebiegła prawidłowo");
            }
            return "admin/dezaktywacja";

        }
        catch (RuntimeException e) {
            // Obsługa błędów i przekazywanie komunikatów do widoku
            if (e.getMessage().contains("email")) {
                model.addAttribute("emailError", e.getMessage());
            }
            // Powrót do formularza z komunikatem o błędzie
            return "admin/dezaktywacja";
        }
    }



}
