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

    // Pobranie id userow na podstawie listy lekarzy
    public List<Long> getIdUserowByIdLekarz(List<Long> idlekarz) {

        String sql = "SELECT iduser FROM lekarz WHERE idlekarz IN (:idlekarz)";

        // Mapowanie parametrów
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idlekarz", idlekarz);

        // Wykonanie zapytania
        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("iduser")
        );
    }

    // Pobranie id lekarzy
    public List<Long> getAllId() {

        String sql = "SELECT idlekarz FROM lekarz";


        // Wykonanie zapytania
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getLong("idlekarz")
        );
    }

    public List<Long> getIdLekarzeByIdUserzy(List<Long> iduserzy){
        String sql = "SELECT idlekarz FROM lekarz WHERE iduser IN (:iduser)";

        // Mapowanie parametrów
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("iduser", iduserzy);

        // Wykonanie zapytania
        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("idlekarz")
        );
    }

    // Metoda mapująca idLekarze -> idUserzy
    public Map<Long, Long> getMappingIdGrafikToIdLekarz(List<Long> idGrafiki) {
        String sql = "SELECT id, idlekarz " +
                "FROM grafik_lekarz " +
                "WHERE id IN (:id)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", idGrafiki);

        // Wykonanie zapytania i mapowanie wyników do mapy
        return jdbcTemplate.query(sql, params, rs -> {
            Map<Long, Long> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getLong("idgrafik"), rs.getLong("idlekarz"));
            }
            return result;
        });
    }







}
