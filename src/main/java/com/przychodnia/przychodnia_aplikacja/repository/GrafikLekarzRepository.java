package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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



    //Pobranie daty na podstawie id lekarza
    public List<LocalDate> getDataByIdLekarz(Long idlekarz) {
        String sql = "SELECT data FROM grafik_lekarz WHERE idlekarz = :idlekarz";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idlekarz", idlekarz);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> {
                    // Pobranie daty w formacie SQL
                    java.sql.Date sqlDate = rs.getDate("data");
                    // Jeśli data jest null, zwracamy null, w przeciwnym razie konwertujemy ją na LocalDate
                    return sqlDate != null ? sqlDate.toLocalDate() : null;
                }
        );
    }

    //Pobranie godziny startowej na podstawie id lekarza
    public List<LocalTime> getGodzinaStartByIdLekarz(Long idlekarz) {
        String sql = "SELECT godzina_startowa FROM grafik_lekarz WHERE idlekarz = :idlekarz";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idlekarz", idlekarz);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> {
                    // Pobranie daty w formacie SQL
                    java.sql.Time sqlTime = rs.getTime("godzina_startowa");
                    // Jeśli data jest null, zwracamy null, w przeciwnym razie konwertujemy ją na LocalTime
                    return sqlTime != null ? sqlTime.toLocalTime() : null;
                }
        );
    }

    //Pobranie godziny koncowej na podstawie id lekarza
    public List<LocalTime> getGodzinaKoniecByIdLekarz(Long idlekarz) {
        String sql = "SELECT godzina_koncowa FROM grafik_lekarz WHERE idlekarz = :idlekarz";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idlekarz", idlekarz);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> {
                    // Pobranie daty w formacie SQL
                    java.sql.Time sqlTime = rs.getTime("godzina_koncowa");
                    // Jeśli data jest null, zwracamy null, w przeciwnym razie konwertujemy ją na LocalTIme
                    return sqlTime != null ? sqlTime.toLocalTime() : null;
                }
        );
    }

    public Long getIdGrafik(Long idlekarz, LocalDate data){
        String sql = "SELECT id FROM grafik_lekarz WHERE idlekarz = :idlekarz AND data = :data LIMIT 1";
        Map<String, Object> params = new HashMap<>();
        params.put("idlekarz", idlekarz);
        params.put("data", data);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    //lista id lekarzy na podstawie id grafiku
    public List<Long> getIdLekarzByIdGrafik(List<Long> idgrafik) {
        String sql = "SELECT idlekarz FROM grafik_lekarz WHERE id IN (:id)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", idgrafik);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("idlekarz")
        );
    }

    //lista dat na podstawie id grafiku
    public List<LocalDate> getDataByIdGrafik(List<Long> idgrafik) {
        String sql = "SELECT data FROM grafik_lekarz WHERE id IN (:id)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", idgrafik);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> {
                    // Pobranie daty w formacie SQL
                    java.sql.Date sqlDate = rs.getDate("data");
                    // Jeśli data jest null, zwracamy null, w przeciwnym razie konwertujemy ją na LocalDate
                    return sqlDate != null ? sqlDate.toLocalDate() : null;
                }
        );

    }

    //czy istnieje data na podstawie id grafiku i danej daty
    public boolean existDataByIdGrafikData(List<Long> idgrafik, LocalDate data) {
        String sql = "SELECT COUNT(*) > 0 FROM grafik_lekarz WHERE id IN (:id) AND data = :data";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", idgrafik);
        params.addValue("data", data);

        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    // Metoda mapująca idGrafiku -> idLekarza
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

    public Long getIdLekrazbyId(Long id){
            String sql = "SELECT idlekarz FROM grafik_lekarz WHERE id = :id";
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);

            return jdbcTemplate.queryForObject(sql, params, Long.class);

    }

    public LocalDate getDataById(Long id){
        String sql = "SELECT data FROM grafik_lekarz WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbcTemplate.queryForObject(sql, params, LocalDate.class);

    }

    public List<Long> getIdGrafikiByIdLekarz(Long idLekarz){

        String sql = "SELECT id FROM grafik_lekarz WHERE idlekarz = :idlekarz";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idlekarz", idLekarz);

            return jdbcTemplate.query(
                    sql,
                    params,
                    (rs, rowNum) -> rs.getLong("id")
            );

    }

    public List<LocalDate> getDataByIdIdLekarzData(List<Long> idGrafiki, Long idLekarz, LocalDate data){
        if (idGrafiki == null || idGrafiki.isEmpty()) {
            return new ArrayList<>(); // Zwróć pustą listę, jeśli brak grafików
        }
        String sql = "SELECT data FROM grafik_lekarz WHERE id IN (:id) AND idlekarz = :idlekarz AND data < :data";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", idGrafiki);
        params.addValue("idlekarz", idLekarz);
        params.addValue("data", data);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> {
                    // Pobranie daty w formacie SQL
                    java.sql.Date sqlDate = rs.getDate("data");
                    // Jeśli data jest null, zwracamy null, w przeciwnym razie konwertujemy ją na LocalDate
                    return sqlDate != null ? sqlDate.toLocalDate() : null;
                }
            );

    }







}
