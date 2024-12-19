package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.model.TerminStatus;
import com.przychodnia.przychodnia_aplikacja.model.TerminyDTO;
import com.przychodnia.przychodnia_aplikacja.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GrafikLekarzService {

    private final GrafikLekarzRepository grafikLekarzRepository;
    private final WizytaRepository wizytaRepository;
    private final UserRepository userRepository;
    private final PacjentRepository pacjentRepository;

    public GrafikLekarzService(GrafikLekarzRepository grafikLekarzRepository, WizytaRepository wizytaRepository, UserRepository userRepository, PacjentRepository pacjentRepository) {
        this.grafikLekarzRepository = grafikLekarzRepository;
        this.wizytaRepository = wizytaRepository;
        this.userRepository = userRepository;
        this.pacjentRepository = pacjentRepository;
    }

    public List<TerminyDTO> grafikLekarz(Long idLekarz) {

        LocalDate dzisiaj = LocalDate.now();
        LocalTime teraz = LocalTime.now();

        List<LocalDate> data = grafikLekarzRepository.getDataByIdLekarz(idLekarz);
        List<LocalTime> godzina_startowa = grafikLekarzRepository.getGodzinaStartByIdLekarz(idLekarz);
        List<LocalTime> godzina_koncowa = grafikLekarzRepository.getGodzinaKoniecByIdLekarz(idLekarz);

        List<TerminyDTO> terminyDTO = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            LocalDate Data = data.get(i);
            LocalTime godzinaStart = godzina_startowa.get(i);
            LocalTime godzinaKoncowa = godzina_koncowa.get(i);

            //Sprawdzenie czy godzina końcowa jest po godzinie startowej
            if (godzinaStart.isBefore(godzinaKoncowa)) {
                TerminyDTO terminDTO = new TerminyDTO(Data, godzinaStart, godzinaKoncowa);
                terminyDTO.add(terminDTO);
            }
        }

        // Sortowanie po dacie, a następnie godzinie startowej
        terminyDTO.sort(Comparator.comparing(TerminyDTO::getData)
                .thenComparing(TerminyDTO::getGodzina_startowa));

        return terminyDTO;
    }

    public List<TerminStatus> generujTerminy(LocalTime start, LocalTime koniec, LocalDate data, Long idlekarz) {
        List<TerminStatus> terminy = new ArrayList<>();
        LocalTime aktualnyCzas = start;

        Long idGrafik = grafikLekarzRepository.getIdGrafik(idlekarz, data);

        // Przechodzimy przez godziny w przedziale start - koniec
        while (aktualnyCzas.plusMinutes(20).isBefore(koniec) || aktualnyCzas.plusMinutes(20).equals(koniec)) {

            String imie="";
            String nazwisko="";

            boolean czyTerminZajety = wizytaRepository.existGodzina(idGrafik, aktualnyCzas);

            if(czyTerminZajety){
                //pobranie imienia i nazwiska pacjenta
                Long idPacjent = wizytaRepository.getIdPacjentByGrafikCzas(idGrafik, aktualnyCzas);
                Long idUser = pacjentRepository.getIdUserByIdPacjent(idPacjent);
                imie = userRepository.getImieById(idUser);
                nazwisko = userRepository.getNazwiskoById(idUser);
            }

            terminy.add(new TerminStatus(aktualnyCzas, czyTerminZajety, imie, nazwisko));

            aktualnyCzas = aktualnyCzas.plusMinutes(25); // 20 minut wizyta + 5 minut przerwy
        }

        return terminy;
    }
}
