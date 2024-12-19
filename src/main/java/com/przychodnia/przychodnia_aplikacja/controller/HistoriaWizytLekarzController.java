package com.przychodnia.przychodnia_aplikacja.controller;

import com.przychodnia.przychodnia_aplikacja.model.DanePacjentaDTO;
import com.przychodnia.przychodnia_aplikacja.model.HistoriaWizytDTO;
import com.przychodnia.przychodnia_aplikacja.model.TerminyDTO;
import com.przychodnia.przychodnia_aplikacja.repository.LekarzRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.service.HistoriaWizytLekarzService;
import com.przychodnia.przychodnia_aplikacja.service.HistoriaWizytService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"loggedInUser", "userRole"})
public class HistoriaWizytLekarzController {

    private final HistoriaWizytLekarzService historiaWizytService;
    private final LekarzRepository lekarzRepository;
    private final UserRepository userRepository;

    public HistoriaWizytLekarzController(HistoriaWizytLekarzService historiaWizytService, LekarzRepository lekarzRepository, UserRepository userRepository) {
        this.historiaWizytService = historiaWizytService;
        this.lekarzRepository = lekarzRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/lekarz/historia_wizyt")
    public String showHistoriaWizytForm(@SessionAttribute(value = "loggedInUser", required = false) String loggedInUser, Model model) {
        // Jeśli użytkownik nie jest zalogowany, przekieruj go na stronę logowania
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            return "redirect:/logowanie";
        }
        // System.out.println(loggedInUser);

        String userRole = (String) model.getAttribute("userRole");
        // Jeśli użytkownik nie jest pacjentem, przekieruj do strony z logowaniem
        if (userRole == null || !userRole.equals("LEKARZ")) {
            return "redirect:/logowanie";
        }

        try{
            Long idUser = userRepository.getIDByEmail(loggedInUser);
            Long idLekarz = lekarzRepository.getIdLekarzByIdUser(idUser);

            List<DanePacjentaDTO> dane = historiaWizytService.showPacjenci(idLekarz);


            // Przekazanie obu list do modelu
            model.addAttribute("dane", dane);
            return "lekarz/historia_wizyt";

        }
        catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());
            if (e.getMessage().contains("Brak")) {
                model.addAttribute("validationError", e.getMessage());
            }
            return "lekarz/historia_wizyt";
        }
    }

    @PostMapping("lekarz/historia_wizyt")
    public String processWyborLekarza(
            @SessionAttribute(value = "loggedInUser", required = false) String loggedInUser,
            @RequestParam("idPacjent") Long iduser,
            @RequestParam("imie") String imie,
            @RequestParam("nazwisko") String nazwisko,
            Model model) {

        try {
            Long idUser = userRepository.getIDByEmail(loggedInUser);
            Long idLekarz = lekarzRepository.getIdLekarzByIdUser(idUser);

            List<LocalDate> historia = historiaWizytService.showDaty(iduser, idLekarz);

            historia = historia.stream()
                    .sorted((d1, d2) -> d2.compareTo(d1))
                    .collect(Collectors.toList());

            model.addAttribute("historiaWizyt", historia);
            model.addAttribute("imie", imie);
            model.addAttribute("nazwisko", nazwisko);
            return "lekarz/pacjent";

        } catch (RuntimeException e) {
            model.addAttribute("validationError", "Wystąpił błąd: " + e.getMessage());
            if (e.getMessage().contains("Brak")) {
                model.addAttribute("validationError", e.getMessage());
            }

            return "lekarz/pacjent";
        }
    }
}

