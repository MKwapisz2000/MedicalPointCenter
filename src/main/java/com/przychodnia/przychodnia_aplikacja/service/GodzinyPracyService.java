package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.repository.GrafikLekarzRepository;
import com.przychodnia.przychodnia_aplikacja.repository.LekarzRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class GodzinyPracyService {

    private final UserRepository userRepository;
    private final LekarzRepository lekarzRepository;
    private final GrafikLekarzRepository grafikLekarzRepository;

    public GodzinyPracyService(UserRepository userRepository, LekarzRepository lekarzRepository, GrafikLekarzRepository grafikLekarzRepository) {
        this.userRepository = userRepository;
        this.lekarzRepository = lekarzRepository;
        this.grafikLekarzRepository = grafikLekarzRepository;
    }

    public void zapiszGodziny(List<String> data, List<String> godzinaStartowa, List<String> godzinaKoncowa, String email) {

        Long IdUser = userRepository.getIDByEmail(email);
        Long IdLekarz = lekarzRepository.getIdLekarzByIdUser(IdUser);

        for (int i = 0; i < data.size(); i++) {
            LocalDate dataDnia = LocalDate.parse(data.get(i));
            LocalTime godzinaStart = LocalTime.parse(godzinaStartowa.get(i));
            LocalTime godzinaKoniec = LocalTime.parse(godzinaKoncowa.get(i));

            // Walidacja godzin (np. rozpoczęcie przed zakończeniem)
            if (godzinaStart.isAfter(godzinaKoniec)) {
                throw new RuntimeException("Godzina rozpoczęcia pracy musi być przed godziną zakończenia pracy.");
            }

            grafikLekarzRepository.saveGrafik(dataDnia, godzinaStart, godzinaKoniec, IdLekarz);
        }


    }
}
