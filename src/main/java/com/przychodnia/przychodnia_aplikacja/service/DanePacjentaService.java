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

    public void edycjaDanych(Long iduser, String imie, String nazwisko, String pesel, String numerTel, String email, String dataUrodzenia,
                             String miasto, String ulica, String nrBudynku, String nrLokalu, String kodPocztowy){

        // Pobierz ID pacjenta
        Long idPacjent = pacjentRepository.getIdPacjentByIdUser(iduser);

        if(!(email.equals(userRepository.getEmailById(iduser))))
        {
            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("Podany email jest już zarejestrowany.");
            }
        }

        if(!(pesel.equals(pacjentRepository.getPeseltByIdPacjent(idPacjent))))
        {
            if (pacjentRepository.existsByPesel(pesel)) {
                throw new RuntimeException("Podany pesel jest już zarejestrowany.");
            }
        }

        // edytuj użytkownika
        userRepository.saveDane(iduser, imie, nazwisko, email);

        // edytuj pacjenta
        pacjentRepository.saveDane(iduser, pesel, dataUrodzenia, numerTel);

        // edytuj adres pacjenta
        adresRepository.saveDane(idPacjent, miasto, ulica, nrBudynku, nrLokalu, kodPocztowy);
    }
}