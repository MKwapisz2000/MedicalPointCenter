package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.model.LekarzDTO;
import com.przychodnia.przychodnia_aplikacja.model.TerminyDTO;
import com.przychodnia.przychodnia_aplikacja.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class WizytaService {

    private final UserRepository userRepository;
    private final LekarzRepository lekarzRepository;
    private final GrafikLekarzRepository grafikLekarzRepository;
    private final PacjentRepository pacjentRepository;
    private final SpecjalizacjaRepository specjalizacjaRepository;
    private final WizytaRepository wizytaRepository;

    public WizytaService(UserRepository userRepository, LekarzRepository lekarzRepository, GrafikLekarzRepository grafikLekarzRepository, PacjentRepository pacjentRepository, SpecjalizacjaRepository specjalizacjaRepository, WizytaRepository wizytaRepository) {
        this.userRepository = userRepository;
        this.lekarzRepository = lekarzRepository;
        this.grafikLekarzRepository = grafikLekarzRepository;
        this.pacjentRepository = pacjentRepository;
        this.specjalizacjaRepository = specjalizacjaRepository;
        this.wizytaRepository = wizytaRepository;
    }

    public List<LekarzDTO> wyborSpecjalizacji(String spec){

        if(!(specjalizacjaRepository.existsBySpec(spec))){
            throw new RuntimeException("Brak lekarzy o wskazanej specjalizacji");
        }

        //lista lekarzy o wybranej specjalizacji
        List<Long> idLekarze = specjalizacjaRepository.getIdLekarzBySpec(spec);

        //pobranie iduserow o na podstawie idlekarzy i statusu aktywnosci
        List<Long> idUser = lekarzRepository.getIdUserByIdLekarz(idLekarze);
        if(idUser.isEmpty()){
            throw new RuntimeException("Brak lekarzy o wskazanej specjalizacji");
        }

        //pobranie listy imion lekarzy na podstawie listy idUserów
        List<String> imiona = userRepository.getImionaByIdUser(idUser);

        //pobranie listy nazwisk lekarzy na podstawie listy idUserów
        List<String> nazwiska = userRepository.getNazwiskaByIdUser(idUser);

        //Tworzymy liste skladajaca sie z powyzszych danych
        List<LekarzDTO> lekarzeDTO = new ArrayList<>();

        for (int i = 0; i < idUser.size(); i++) {
            String imie = imiona.get(i);    // Pobieramy odpowiednie imię
            String nazwisko = nazwiska.get(i);  // Pobieramy odpowiednie nazwisko
            Long idLekarz = idLekarze.get(i);   // Pobieramy odpowiednie ID lekarza

            // Tworzymy nowy obiekt LekarzDTO i dodajemy go do listy
            LekarzDTO lekarzDTO = new LekarzDTO(idLekarz, imie, nazwisko);
            lekarzeDTO.add(lekarzDTO);
        }

        return lekarzeDTO;
    }

    public List<TerminyDTO> wyborTerminu(Long idlekarz) {

        LocalDate dzisiaj = LocalDate.now();
        LocalTime teraz = LocalTime.now();

        List<LocalDate> data = grafikLekarzRepository.getDataByIdLekarz(idlekarz);
        List<LocalTime> godzina_startowa = grafikLekarzRepository.getGodzinaStartByIdLekarz(idlekarz);
        List<LocalTime> godzina_koncowa = grafikLekarzRepository.getGodzinaKoniecByIdLekarz(idlekarz);

        List<TerminyDTO> terminyDTO = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            LocalDate Data = data.get(i);
            LocalTime godzinaStart = godzina_startowa.get(i);
            LocalTime godzinaKoncowa = godzina_koncowa.get(i);

            // Odrzucanie dat wcześniejszych niż dzisiejsza
            if (Data.isBefore(dzisiaj)) {
                continue;
            }

            // Jeśli data to dzisiejszy dzień, odrzuć godziny, które już minęły
            if (Data.isEqual(dzisiaj)) {

                teraz =LocalTime.now().withSecond(0).withNano(0);

                godzinaStart = (godzinaStart.isBefore(teraz)) ? teraz : godzinaStart;
            }

            //Sprawdzenie czy godzina końcowa jest po godzinie startowej
            if (godzinaStart.isBefore(godzinaKoncowa)) {
                TerminyDTO terminDTO = new TerminyDTO(Data, godzinaStart, godzinaKoncowa);
                terminyDTO.add(terminDTO);
                System.out.println(terminDTO.getData());
                System.out.println(terminDTO.getGodzina_startowa());
                System.out.println(terminDTO.getGodzina_koncowa());
            }

        }

        // Sortowanie po dacie, a następnie godzinie startowej
        terminyDTO.sort(Comparator.comparing(TerminyDTO::getData)
                .thenComparing(TerminyDTO::getGodzina_startowa));

        return terminyDTO;
    }


    public List<LocalTime> generujDostepneTerminy(LocalTime start, LocalTime koniec, LocalDate data, Long idlekarz) {
        List<LocalTime> terminy = new ArrayList<>();
        LocalTime aktualnyCzas = start;

        Long idGrafik = grafikLekarzRepository.getIdGrafik(idlekarz, data);

        // Przechodzimy przez godziny w przedziale start - koniec
        while (aktualnyCzas.plusMinutes(20).isBefore(koniec) || aktualnyCzas.plusMinutes(20).equals(koniec)) {

            boolean czyTerminZajety = wizytaRepository.existGodzina(idGrafik, aktualnyCzas);
            if(!czyTerminZajety){
                terminy.add(aktualnyCzas);
            }

            aktualnyCzas = aktualnyCzas.plusMinutes(25); // 20 minut wizyta + 5 minut przerwy
        }

        return terminy;
    }


    public void saveWizyta(LocalTime godzina, LocalDate data, String email, Long idlekarz){

        Long idUser = userRepository.getIDByEmail(email);
        Long idPacjent = pacjentRepository.getIdPacjentByIdUser(idUser);
        Long idgrafik = grafikLekarzRepository.getIdGrafik(idlekarz, data);

        wizytaRepository.saveWizyta(idPacjent, idgrafik, "ZAREZERWOWANA", godzina);
    }


}
