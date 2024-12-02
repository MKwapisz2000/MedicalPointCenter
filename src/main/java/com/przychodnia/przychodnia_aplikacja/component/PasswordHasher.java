package com.przychodnia.przychodnia_aplikacja.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {
    private final BCryptPasswordEncoder encoder;

    public PasswordHasher() {
        this.encoder = new BCryptPasswordEncoder();
    }

    // Hashowanie hasła
    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Weryfikacja hasła
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
