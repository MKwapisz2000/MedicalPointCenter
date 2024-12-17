package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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

    // Pobierz listę ID userów o danych idlekarzy
    public List<Long> getIdUserByIdLekarz(List<Long> idLekarz) {
        // Zapytanie SQL z JOIN między tabelami lekarz i user, z filtrowaniem po statusie
        String sql = "SELECT l.iduser " +
                "FROM lekarz l " +
                "JOIN user u ON l.iduser = u.iduser " +
                "WHERE l.idlekarz IN (:idlekarz) " +
                "AND u.status = 'AKTYWNY'";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idlekarz", idLekarz);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("iduser")
        );
    }

}
