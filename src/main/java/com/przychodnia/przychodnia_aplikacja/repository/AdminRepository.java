package com.przychodnia.przychodnia_aplikacja.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AdminRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AdminRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Metoda do pobrania idadmin na podstawie iduser
    public Long getIdAdminByIdUser(Long idUser) {
        String sql = "SELECT idadmin FROM admin WHERE iduser = :iduser";
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", idUser);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }
}
