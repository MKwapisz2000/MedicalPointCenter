package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    // Pobierz listę ID lekarzy o danej specjalizacji
    public List<Long> getIdLekarzBySpec(String spec) {
        String sql = "SELECT idlekarz FROM specjalizacja WHERE spec = :spec";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("spec", spec);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("idlekarz")
        );
    }

    //Sprawdzenie czy specjalizacja istnieje
    public boolean existsBySpec(String spec) {
        String sql = "SELECT COUNT(*) > 0 FROM specjalizacja WHERE spec = :spec";
        Map<String, Object> params = new HashMap<>();
        params.put("spec", spec);

        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }




}
