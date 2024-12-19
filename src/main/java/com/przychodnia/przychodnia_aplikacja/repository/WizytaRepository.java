package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WizytaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WizytaRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Zapis nowej wizyty
    public void saveWizyta(Long idpacjent, Long idgrafik, String status, LocalTime godzina) {
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

    //Metoda do pobrania id grafiku lekarza danego pacjenta
    public List<Long> getIdGrafikByIdPacjent(Long idpacjent) {
        String sql = "SELECT idgrafik FROM wizyta WHERE idpacjent = :idpacjent";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpacjent", idpacjent);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("idgrafik")
        );
    }

    //Pobranie godzin wizyt na podstawie id pacjenta
    public List<LocalTime> getGodzinaByIdPacjent(Long idpacjent) {
        String sql = "SELECT godzina FROM wizyta WHERE idpacjent = :idpacjent";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpacjent", idpacjent);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> {
                    // Pobranie daty w formacie SQL
                    java.sql.Time sqlTime = rs.getTime("godzina");
                    // Jeśli data jest null, zwracamy null, w przeciwnym razie konwertujemy ją na LocalTIme
                    return sqlTime != null ? sqlTime.toLocalTime() : null;
                }
        );
    }

    //Metoda do pobrania id grafiku lekarza danego pacjenta
    public List<String> getStatusyByIdPacjent(Long idpacjent) {
        String sql = "SELECT status FROM wizyta WHERE idpacjent = :idpacjent";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpacjent", idpacjent);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getString("status")
        );
    }

    public Long getWizytaIdByPacjentAndGrafik(Long idpacjent, Long idgrafik, LocalTime godzina){
        String sql = "SELECT idwizyta FROM wizyta WHERE idpacjent = :idpacjent AND idgrafik = :idgrafik AND godzina = :godzina";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);
        params.put("idgrafik", idgrafik);
        params.put("godzina", godzina);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public void updateStatusById(Long idwizyta, String status) {
        String sql = """
                    UPDATE wizyta SET status = :status WHERE idwizyta = :idwizyta
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("idwizyta", idwizyta);
        params.put("status", status);

        // Wstawienie użytkownika do bazy danych
        jdbcTemplate.update(sql, params);
    }

    // Sprawdzanie, czy użytkownik ma jakiekolwiek wizyty
    public boolean existsByIdPacjent(Long idpacjent) {
        String sql = "SELECT COUNT(*) > 0 FROM wizyta WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    //pobranie listy idgrafiku dla wierszy o idpacjent, godzinie w przedziale termin+-20minut i statusie ZAREZERWOWANA
    public List<Long> getIdGrafikListByidPacjentStatusGodzina(Long idpacjent, String status, LocalTime godzina){
        LocalTime godzinaMin = godzina.minusMinutes(20);
        LocalTime godzinaMax = godzina.plusMinutes(20);

        String sql = "SELECT idgrafik FROM wizyta WHERE idpacjent = :idpacjent AND status = :status AND godzina >= :godzinaMin AND godzina <= :godzinaMax";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpacjent", idpacjent);
        params.addValue("status", status);
        params.addValue("godzinaMin", godzinaMin);
        params.addValue("godzinaMax", godzinaMax);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> rs.getLong("idgrafik")
        );
    }

    public Long getIdPacjentByGrafikCzas(Long idgrafik, LocalTime godzina){

        String sql = "SELECT idpacjent FROM wizyta WHERE idgrafik = :idgrafik AND godzina = :godzina";
        Map<String, Object> params = new HashMap<>();
        params.put("idgrafik", idgrafik);
        params.put("godzina", godzina);

        return jdbcTemplate.queryForObject(sql, params, Long.class);

    }



}
