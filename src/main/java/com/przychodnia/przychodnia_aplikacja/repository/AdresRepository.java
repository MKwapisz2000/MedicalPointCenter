package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AdresRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AdresRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Zapis nowego adresu
    public void saveAdres(Long idPacjent, String miasto, String ulica, String nrBudynku, String nrLokalu, String kodPocztowy) {
        String checkSql = "SELECT COUNT(*) FROM pacjent WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idPacjent);

        boolean exists = jdbcTemplate.queryForObject(checkSql, params, Boolean.class);
        if (!exists) {
            throw new RuntimeException("Pacjent o podanym ID nie istnieje.");
        }
        else{
            System.out.println(idPacjent);
        }

        String sql = """
            INSERT INTO adres (idpacjent, miasto, ulica, nr_budynku, nr_lokalu, kod_pocztowy)
            VALUES (:idpacjent, :miasto, :ulica, :nrBudynku, :nrLokalu, :kodPocztowy)
        """;
        Map<String, Object> params2 = new HashMap<>();
        params2.put("idpacjent", idPacjent);
        params2.put("miasto", miasto);
        params2.put("ulica", ulica);
        params2.put("nrBudynku", nrBudynku);
        params2.put("nrLokalu", nrLokalu);
        params2.put("kodPocztowy", kodPocztowy);

        jdbcTemplate.update(sql, params2);

        System.out.println("Dane do zapisania: " + params2);

    }

    // Metoda do pobrania idadres na podstawie idpacjenta
    public Long getIdAdresByIdPacjent(Long idpacjent) {
        String sql = "SELECT idadres FROM adres WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    // Metoda do pobrania miasta na podstawie idpacjenta
    public String getMiastoByIdPacjent(Long idpacjent) {
        String sql = "SELECT miasto FROM adres WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania ulicy na podstawie idpacjenta
    public String getUlicaByIdPacjent(Long idpacjent) {
        String sql = "SELECT ulica FROM adres WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania numeru budynku na podstawie idpacjenta
    public String getNrBudynkuByIdPacjent(Long idpacjent) {
        String sql = "SELECT nr_budynku FROM adres WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania numeru lokalu na podstawie idpacjenta
    public String getNrLokaluByIdPacjent(Long idpacjent) {
        String sql = "SELECT nr_lokalu FROM adres WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Metoda do pobrania kodu pocztowego na podstawie idpacjenta
    public String getKodPocztowyByIdPacjent(Long idpacjent) {
        String sql = "SELECT kod_pocztowy FROM adres WHERE idpacjent = :idpacjent";
        Map<String, Object> params = new HashMap<>();
        params.put("idpacjent", idpacjent);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // Zmiana danych użytkownika
    public void saveDane(Long idpacjent, String miasto, String ulica, String nr_budynku, String nr_lokalu, String kod_pocztowy) {
        String sql = """
                    UPDATE adres SET miasto = :miasto, ulica = :ulica, nr_budynku = :nr_budynku, nr_lokalu = :nr_lokalu, kod_pocztowy = :kod_pocztowy
                     WHERE idpacjent = :idpacjent
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("miasto", miasto);
        params.put("ulica", ulica);
        params.put("nr_budynku", nr_budynku);
        params.put("nr_lokalu", nr_lokalu);
        params.put("kod_pocztowy", kod_pocztowy);
        params.put("idpacjent", idpacjent);

        // Wstawienie adresu do bazy danych
        jdbcTemplate.update(sql, params);
    }

}
