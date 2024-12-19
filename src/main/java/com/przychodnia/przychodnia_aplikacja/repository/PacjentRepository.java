package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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
    public Long getIdPacjentByIdUser(Long iduser) {
        String sql = "SELECT idpacjent FROM pacjent WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", iduser);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    // Metoda do pobrania pesla na podstawie idpacjenta
    public String getPeseltByIdPacjent(Long idpacjent) {
        String sql = "SELECT pesel FROM pacjent WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania daty_ur na podstawie idpacjenta
    public String getDataUrByIdPacjent(Long idpacjent) {
        String sql = "SELECT data_urodzenia FROM pacjent WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania numeru_tel na podstawie idpacjenta
    public String getNumerTelByIdPacjent(Long idpacjent) {
        String sql = "SELECT numer_tel FROM pacjent WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    public void saveDane(Long iduser, String pesel, String data_urodzenia, String numer_tel) {
        String sql = """
                    UPDATE pacjent SET pesel = :pesel, data_urodzenia = :data_urodzenia, numer_tel = :numer_tel WHERE iduser = :iduser
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("pesel", pesel);
        params.put("data_urodzenia", data_urodzenia);
        params.put("numer_tel", numer_tel);
        params.put("iduser", iduser);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);
    }

    public Long getIdUserByIdPacjent(Long idPacjent){
        String sql = "SELECT iduser FROM pacjent WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idPacjent);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public List<Long> getIdUserzyByIdPacjenci(List<Long> idPacjenci){
        String sql = "SELECT iduser FROM pacjent WHERE idpacjent IN (:idpacjent)";

        // Mapowanie parametrów
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpacjent", idPacjenci);

        // Wykonanie zapytania
        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("iduser")
        );
    }
}
