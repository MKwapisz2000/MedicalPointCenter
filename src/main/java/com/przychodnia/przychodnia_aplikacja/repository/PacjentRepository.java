package com.przychodnia.przychodnia_aplikacja.repository;

import com.przychodnia.przychodnia_aplikacja.model.Pacjent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PacjentRepository extends JpaRepository<Pacjent, Long> {

    Optional<Pacjent> findByPesel(String pesel);

    Optional<Pacjent> findByUser_Iduser(Long iduser);

    boolean existsByPesel(String pesel);

    boolean existsByNumerTel(String numerTel);


}
