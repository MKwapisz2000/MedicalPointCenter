package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.component.PasswordHasher;
import com.przychodnia.przychodnia_aplikacja.repository.AdresRepository;
import com.przychodnia.przychodnia_aplikacja.repository.PacjentRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RejestracjaService {

    private final UserRepository userSQLRepository;
    private final PacjentRepository pacjentSQLRepository;
    private final AdresRepository adresSQLRepository;
    private final PasswordHasher passwordHasher;

    public RejestracjaService(UserRepository userSQLRepository, PacjentRepository pacjentSQLRepository, AdresRepository adresSQLRepository, PasswordHasher passwordHasher) {
        this.userSQLRepository = userSQLRepository;
        this.pacjentSQLRepository = pacjentSQLRepository;
        this.adresSQLRepository = adresSQLRepository;
        this.passwordHasher = passwordHasher;
    }

    public void rejestracjaNewPatient(String imie, String nazwisko, String pesel, String numerTel, String email, String haslo, String dataUrodzenia,
                                      String miasto, String ulica, String nrBudynku, String nrLokalu, String kodPocztowy) {
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

        // Pobierz ID pacjenta
        Long idPacjent = pacjentSQLRepository.getIdPacjentByIdUser(idUser);

        // Zapisz adres pacjenta
        adresSQLRepository.saveAdres(idPacjent, miasto, ulica, nrBudynku, nrLokalu, kodPocztowy);
    }
}
