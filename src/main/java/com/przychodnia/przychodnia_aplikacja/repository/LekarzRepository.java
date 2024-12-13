package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LekarzRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LekarzRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Metoda do pobrania idplekarz na podstawie iduser
    public Long getIdLekarzByIdUser(Long idUser) {
        String sql = "SELECT idlekarz FROM lekarz WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", idUser);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    // Zapis nowego lekarza
    public void saveLekarz(Long iduser) {
        String sql = """
            INSERT INTO lekarz (iduser)
            VALUES (:iduser)
        """;
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        jdbcTemplate.update(sql, params);
    }
}
