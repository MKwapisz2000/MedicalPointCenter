package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
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

    // Metoda do pobrania id usera na podstawie wprowadzonego emaila
    public Long getIDByEmail(String email) {
        String sql = "SELECT iduser FROM user WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    // Metoda do pobrania typu usera na podstawie wprowadzonego emaila
    public String getTypByEmail(String email) {
        String sql = "SELECT typ FROM user WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania hasła hashowanego na podstawie id usera
    public String getHasloById(Long iduser) {
        String sql = "SELECT haslo FROM user WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania typu usera na podstawie wprowadzonego id usera
    public String getTypById(Long iduser) {
        String sql = "SELECT typ FROM user WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania statusu usera na podstawie wprowadzonego id usera
    public String getStatusById(Long iduser) {
        String sql = "SELECT status FROM user WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania emaila usera na podstawie wprowadzonego id usera
    public String getEmailById(Long iduser) {
        String sql = "SELECT email FROM user WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania imienia usera na podstawie wprowadzonego id usera
    public String getImieById(Long iduser) {
        String sql = "SELECT imie FROM user WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania nazwiska usera na podstawie wprowadzonego id usera
    public String getNazwiskoById(Long iduser) {
        String sql = "SELECT nazwisko FROM user WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Pobranie imion lekarzy na podstawie listy userów
    public List<String> getImionaByIdUser(List<Long> idUser) {
        // Zapytanie SQL do pobrania imion i nazwisk użytkowników
        String sql = "SELECT imie FROM user WHERE iduser IN (:iduser)";

        // Mapowanie parametrów
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("iduser", idUser);

        // Wykonanie zapytania
        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getString("imie")
        );
    }

    // Pobranie imion lekarzy na podstawie listy userów
    public List<String> getNazwiskaByIdUser(List<Long> idUser) {
        // Zapytanie SQL do pobrania imion i nazwisk użytkowników
        String sql = "SELECT nazwisko FROM user WHERE iduser IN (:iduser)";

        // Mapowanie parametrów
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("iduser", idUser);

        // Wykonanie zapytania
        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getString("nazwisko")
        );
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

    // Zmiana hasla użytkownika
    public void saveHaslo(String haslo, Long iduser) {
        String sql = """
                    UPDATE user SET haslo = :haslo WHERE iduser = :iduser
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("haslo", haslo);
        params.put("iduser", iduser);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);
    }

    // Zmiana danych użytkownika
    public void saveDane(Long iduser, String imie, String nazwisko, String email) {
        String sql = """
                    UPDATE user SET imie = :imie, nazwisko = :nazwisko, email = :email WHERE iduser = :iduser
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("imie", imie);
        params.put("nazwisko", nazwisko);
        params.put("email", email);
        params.put("iduser", iduser);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);
    }

    // Dezaktywacja użytkownika
    public void dez_aktywacja(String email, String action){
        String sql = """
                    UPDATE user SET status = :status WHERE email = :email
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("status", action);
        params.put("email", email);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);
    }
}
