package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.repository.*;
import org.springframework.stereotype.Service;

@Service
public class HistoriaWizytService {

    private final UserRepository userRepository;
    private final LekarzRepository lekarzRepository;
    private final GrafikLekarzRepository grafikLekarzRepository;
    private final PacjentRepository pacjentRepository;
    private final SpecjalizacjaRepository specjalizacjaRepository;
    private final WizytaRepository wizytaRepository;

    public HistoriaWizytService(UserRepository userRepository, LekarzRepository lekarzRepository, GrafikLekarzRepository grafikLekarzRepository, PacjentRepository pacjentRepository, SpecjalizacjaRepository specjalizacjaRepository, WizytaRepository wizytaRepository) {
        this.userRepository = userRepository;
        this.lekarzRepository = lekarzRepository;
        this.grafikLekarzRepository = grafikLekarzRepository;
        this.pacjentRepository = pacjentRepository;
        this.specjalizacjaRepository = specjalizacjaRepository;
        this.wizytaRepository = wizytaRepository;
    }
/*
    public List<WizytaDTO> getHistory(String email){
        Long idUser = userRepository.getIDByEmail(email);
        Long idPacjent = pacjentRepository.getIdPacjentByIdUser(idUser);



    }*/
}

