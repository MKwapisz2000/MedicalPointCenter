package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.component.PasswordHasher;
import com.przychodnia.przychodnia_aplikacja.repository.PacjentRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RejestracjaService {

    private final UserRepository userSQLRepository;
    private final PacjentRepository pacjentSQLRepository;
    private final PasswordHasher passwordHasher;

    public RejestracjaService(UserRepository userSQLRepository, PacjentRepository pacjentSQLRepository, PasswordHasher passwordHasher) {
        this.userSQLRepository = userSQLRepository;
        this.pacjentSQLRepository = pacjentSQLRepository;
        this.passwordHasher = passwordHasher;
    }

    public void rejestracjaNewPatient(String imie, String nazwisko, String pesel, String numerTel, String email, String haslo, String dataUrodzenia) {
        if (userSQLRepository.existsByEmail(email)) {
            throw new RuntimeException("Email jest już zarejestrowany.");
        }
        if (pacjentSQLRepository.existsByPesel(pesel)) {
            throw new RuntimeException("Pesel jest już zarejestrowany.");
        }

        // Hashowanie hasła
        String hashedPassword = passwordHasher.hashPassword(haslo);

        // Zapisz użytkownika
        Long idUser = userSQLRepository.saveUser(imie, nazwisko, email, hashedPassword, "PACJENT", "AKTYWNY");

        // Zapisz pacjenta
        pacjentSQLRepository.savePacjent(idUser, pesel, dataUrodzenia, numerTel);
    }
}
