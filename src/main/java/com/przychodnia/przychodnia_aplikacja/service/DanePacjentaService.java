package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.model.DanePacjentaDTO;
import com.przychodnia.przychodnia_aplikacja.repository.*;
import org.springframework.stereotype.Service;

@Service
public class DanePacjentaService {

    private final UserRepository userRepository;
    private final PacjentRepository pacjentRepository;
    private final AdresRepository adresRepository;


    public DanePacjentaService(UserRepository userRepository, PacjentRepository pacjentRepository, AdresRepository adresRepository) {
        this.userRepository = userRepository;
        this.pacjentRepository = pacjentRepository;
        this.adresRepository = adresRepository;
    }

    public DanePacjentaDTO showDane(Long IdUser){

        String imie = userRepository.getImieById(IdUser);
        String nazwisko = userRepository.getNazwiskoById(IdUser);
        String email = userRepository.getEmailById(IdUser);

        Long IdPacjent = pacjentRepository.getIdPacjentByIdUser(IdUser);
        String pesel = pacjentRepository.getPeseltByIdPacjent(IdPacjent);
        String dataUrodzenia = pacjentRepository.getDataUrByIdPacjent(IdPacjent);
        String numerTelefonu = pacjentRepository.getNumerTelByIdPacjent(IdPacjent);

        String miasto = adresRepository.getMiastoByIdPacjent(IdPacjent);
        String ulica = adresRepository.getUlicaByIdPacjent(IdPacjent);
        String nrBudynku = adresRepository.getNrBudynkuByIdPacjent(IdPacjent);
        String nrLokalu = adresRepository.getNrLokaluByIdPacjent(IdPacjent);
        String kodPocztowy = adresRepository.getKodPocztowyByIdPacjent(IdPacjent);

        return new DanePacjentaDTO(imie, nazwisko, pesel, dataUrodzenia, numerTelefonu, email, miasto, ulica, nrBudynku, nrLokalu, kodPocztowy);
    }
}