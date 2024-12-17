package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class WizytaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WizytaRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Zapis nowej wizyty
    public Long saveWizyta(Long idpacjent, Long idgrafik, String status, LocalTime godzina) {
        String sql = """
            INSERT INTO wizyta (idpacjent, idgrafik, status, godzina)
            VALUES (:idpacjent, :idgrafik, :status, :godzina)
        """;
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);
        params.put("idgrafik", idgrafik);
        params.put("status", status);
        params.put("godzina", godzina);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);

        // Pobranie ID nowo dodanego użytkownika
        String getIdSql = "SELECT idwizyta FROM wizyta WHERE idpacjent = :idpacjent AND idgrafik = :idgrafik";
        return jdbcTemplate.queryForObject(getIdSql, params, Long.class);
    }

    public Long getIdWizytatByIdGrafik(Long idgrafik){
        String sql = "SELECT idwizyta FROM wizyta WHERE idgrafik = :idgrafik";
        Map<String, Object> params = new HashMap<>();
        params.put("idgrafik", idgrafik);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public boolean existGodzina(Long idgrafik, LocalTime godzina) {
        String sql = "SELECT COUNT(*) > 0 FROM wizyta WHERE idgrafik = :idgrafik AND godzina = :godzina";
        Map<String, Object> params = new HashMap<>();
        params.put("idgrafik", idgrafik);
        params.put("godzina", godzina);

        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }


}
