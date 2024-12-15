package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class GrafikLekarzRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GrafikLekarzRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Zapis nowego grafiku
    public void saveGrafik(LocalDate data, LocalTime godzina_startowa, LocalTime godzina_koncowa, Long idlekarz) {
        String sql = """
            INSERT INTO grafik_lekarz (idlekarz, data, godzina_startowa, godzina_koncowa)
            VALUES (:idlekarz, :data, :godzina_startowa, :godzina_koncowa)
        """;
        Map<String, Object> params = new HashMap<>();
        params.put("idlekarz", idlekarz);
        params.put("data", data);
        params.put("godzina_startowa", godzina_startowa);
        params.put("godzina_koncowa", godzina_koncowa);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);
    }
}
