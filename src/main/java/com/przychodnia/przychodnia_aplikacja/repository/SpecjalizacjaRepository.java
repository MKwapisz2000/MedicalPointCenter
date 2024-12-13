package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SpecjalizacjaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SpecjalizacjaRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Zapis nowegej specjalizacji
    public void saveSpecjalizacja(Long idlekarz, List<String> spec) {
        String sql = """
            INSERT INTO specjalizacja (idlekarz, spec)
            VALUES (:idlekarz, :spec)
        """;

        for (String specjalizacja : spec) {
            System.out.println(specjalizacja);
            Map<String, Object> params = new HashMap<>();
            params.put("idlekarz", idlekarz);
            params.put("spec", specjalizacja);

            jdbcTemplate.update(sql, params);
        }

    }
}
