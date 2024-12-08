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
}
