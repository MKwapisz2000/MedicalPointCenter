package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.component.PasswordHasher;
import com.przychodnia.przychodnia_aplikacja.repository.AdminRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NowyAdministratorService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordHasher passwordHasher;

    public NowyAdministratorService(UserRepository userRepository, AdminRepository adminRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordHasher = passwordHasher;
    }

    public void newAdministrator(String imie, String nazwisko, String email, String haslo) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Administrator o takim emailu już istnieje!");
        }

        // Hashowanie hasła
        String hashedPassword = passwordHasher.hashPassword(haslo);

        // Zapisz użytkownika
        Long idUser = userRepository.saveUser(imie, nazwisko, email, hashedPassword, "ADMIN", "AKTYWNY");

        // Zapisz admina
        adminRepository.saveAdmin(idUser);
    }
}
