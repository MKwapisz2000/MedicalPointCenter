package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.TerminStatus;
import com.przychodnia.przychodnia_aplikacja.model.TerminyDTO;
import com.przychodnia.przychodnia_aplikacja.repository.LekarzRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.GrafikLekarzService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalTime;
import java.util.List;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class GrafikLekarzController {

    private final GrafikLekarzService grafikLekarzService;
    private final UserRepository userRepository;
    private final LekarzRepository lekarzRepository;

    public GrafikLekarzController(GrafikLekarzService grafikLekarzService, UserRepository userRepository, LekarzRepository lekarzRepository) {
        this.grafikLekarzService = grafikLekarzService;
        this.userRepository = userRepository;
        this.lekarzRepository = lekarzRepository;
    }

    @GetMapping("/lekarz/grafik")
    public String showGrafikLekarzForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest adminem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("LEKARZ")) {
            return "redirect:/logowanie";
        }

        try{
            Long idUser = userRepository.getIDByEmail(loggedInUser);
            Long idLekarz = lekarzRepository.getIdLekarzByIdUser(idUser);

            List<TerminyDTO> terminy = grafikLekarzService.grafikLekarz(idLekarz);

            for (TerminyDTO termin : terminy) {
                List<TerminStatus> godzinyGrafik = grafikLekarzService.generujTerminy(termin.getGodzina_startowa(), termin.getGodzina_koncowa(), termin.getData(), idLekarz);
                termin.setGodzinyGrafik(godzinyGrafik);
            }
            model.addAttribute("terminy", terminy);

        } catch (RuntimeException e) {
            System.err.println("Błąd: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());
        }

        return "lekarz/grafik";
    }
}
