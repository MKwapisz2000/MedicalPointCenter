package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.component.PasswordHasher;
import com.przychodnia.przychodnia_aplikacja.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RejestracjaLekarzService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final LekarzRepository lekarzRepository;
    private final SpecjalizacjaRepository specjalizacjaRepository;

    public RejestracjaLekarzService(UserRepository userSQLRepository, PasswordHasher passwordHasher, LekarzRepository lekarzRepository, SpecjalizacjaRepository specjalizacjaRepository) {
        this.userRepository = userSQLRepository;
        this.passwordHasher = passwordHasher;
        this.lekarzRepository = lekarzRepository;
        this.specjalizacjaRepository = specjalizacjaRepository;
    }


    public void rejestracjaNewLekarz(String imie, String nazwisko, String email, String haslo, List<String> spec) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Podany email jest już zarejestrowany.");
        }



        // Hashowanie hasła
        String hashedPassword = passwordHasher.hashPassword(haslo);

        // Zapisz użytkownika
        Long idUser = userRepository.saveUser(imie, nazwisko, email, hashedPassword, "LEKARZ", "AKTYWNY");

        // Zapisz lekarza
        lekarzRepository.saveLekarz(idUser);

        // Pobierz ID lekarza
        Long idLekarz = lekarzRepository.getIdLekarzByIdUser(idUser);

        System.out.println(imie);
        System.out.println(nazwisko);
        System.out.println(email);
        System.out.println(haslo);
        System.out.println(spec);
        System.out.println(idUser);
        System.out.println(idLekarz);

        // Zapisz specjalizacje lekarza
        specjalizacjaRepository.saveSpecjalizacja(idLekarz, spec);

    }
}
