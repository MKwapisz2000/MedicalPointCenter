package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DezaktywacjaService {

    private final UserRepository userRepository;

    public DezaktywacjaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void dezaktywacjaUser(String email, String action) {
        if (!(userRepository.existsByEmail(email))) {
            throw new RuntimeException("Użytkownik o takim emailu nie jest zarejestrowany.");
        }

        userRepository.dez_aktywacja(email, action);
    }
}
