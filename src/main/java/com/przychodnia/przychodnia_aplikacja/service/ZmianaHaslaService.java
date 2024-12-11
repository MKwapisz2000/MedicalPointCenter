package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.component.PasswordHasher;
import com.przychodnia.przychodnia_aplikacja.repository.AdresRepository;
import com.przychodnia.przychodnia_aplikacja.repository.PacjentRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ZmianaHaslaService {

    private final UserRepository userSQLRepository;
    private final PasswordHasher passwordHasher;

    public ZmianaHaslaService(UserRepository userSQLRepository, PasswordHasher passwordHasher) {
        this.userSQLRepository = userSQLRepository;
        this.passwordHasher = passwordHasher;
    }

    public void zmianaHasla(String haslo1, Long idUser) {

        // Hashowanie hasła
        String hashedPassword = passwordHasher.hashPassword(haslo1);

        // Zapisz użytkownika
        userSQLRepository.saveHaslo(hashedPassword, idUser);
    }
}