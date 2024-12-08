package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PacjentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PacjentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Sprawdzanie, czy pacjent istnieje na podstawie PESEL
    public boolean existsByPesel(String pesel) {
        String sql = "SELECT COUNT(*) > 0 FROM pacjent WHERE pesel = :pesel";
        Map<String, Object> params = new HashMap<>();
        params.put("pesel", pesel);

        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    // Zapis nowego pacjenta
    public void savePacjent(Long idUser, String pesel, String dataUrodzenia, String numerTel) {
        String sql = """
            INSERT INTO pacjent (iduser, pesel, data_urodzenia, numer_tel)
            VALUES (:iduser, :pesel, :dataUrodzenia, :numerTel)
        """;
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", idUser);
        params.put("pesel", pesel);
        params.put("dataUrodzenia", dataUrodzenia);
        params.put("numerTel", numerTel);

        jdbcTemplate.update(sql, params);
    }

    // Metoda do pobrania idpacjent na podstawie iduser
    public Long getIdPacjentByIdUser(Long idUser) {
        String sql = "SELECT idpacjent FROM pacjent WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", idUser);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }
}
