package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.component.PasswordHasher;
import com.przychodnia.przychodnia_aplikacja.model.Pacjent;
import com.przychodnia.przychodnia_aplikacja.model.User;
import com.przychodnia.przychodnia_aplikacja.repository.PacjentRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RejestracjaService {

    private final UserRepository userRepository;
    private final PacjentRepository pacjentRepository;
    private final PasswordHasher passwordHasher;

    public RejestracjaService(UserRepository userRepository, PacjentRepository pacjentRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.pacjentRepository = pacjentRepository;
        this.passwordHasher = passwordHasher;
    }

    public void rejestracjaNewPatient(String imie, String nazwisko, String pesel, String numerTel, String email, String haslo, String dataUrodzenia) {
        // Sprawdź, czy email i PESEL są unikalne
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email jest już zarejestrowany.");
        }
        if (pacjentRepository.existsByPesel(pesel)) {
            throw new RuntimeException("Pesel jest już zarejestrowany.");
        }

        // Tworzenie użytkownika
        User user = new User();
        user.setImie(imie);
        user.setNazwisko(nazwisko);
        user.setEmail(email);

        // Hashowanie hasła
        String hashedPassword = passwordHasher.hashPassword(haslo);
        System.out.println("Zahashowane hasło: " + hashedPassword); // Loguj zahashowane hasło
        user.setHaslo(hashedPassword);


        user.setTyp(User.TypUzytkownika.PACJENT);
        user.setStatus(User.Status.AKTYWNY);

        // Zapisz użytkownika
        userRepository.save(user);

        // Tworzenie pacjenta
        Pacjent pacjent = new Pacjent();
        pacjent.setUser(user);
        pacjent.setPesel(pesel);
        pacjent.setNumerTel(numerTel);
        pacjent.setDataUrodzenia(LocalDate.parse(dataUrodzenia)); // Konwersja String -> LocalDate

        // Zapisz pacjenta
        pacjentRepository.save(pacjent);
    }
}
