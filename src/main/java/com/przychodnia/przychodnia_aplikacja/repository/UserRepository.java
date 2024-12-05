package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Sprawdzanie, czy użytkownik istnieje na podstawie email
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) > 0 FROM user WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    // Zapis nowego użytkownika
    public Long saveUser(String imie, String nazwisko, String email, String haslo, String typ, String status) {
        String sql = """
            INSERT INTO user (imie, nazwisko, email, haslo, typ, status)
            VALUES (:imie, :nazwisko, :email, :haslo, :typ, :status)
        """;
        Map<String, Object> params = new HashMap<>();
        params.put("imie", imie);
        params.put("nazwisko", nazwisko);
        params.put("email", email);
        params.put("haslo", haslo);
        params.put("typ", typ);
        params.put("status", status);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);

        // Pobranie ID nowo dodanego użytkownika
        String getIdSql = "SELECT iduser FROM user WHERE email = :email";
        return jdbcTemplate.queryForObject(getIdSql, params, Long.class);
    }
}
