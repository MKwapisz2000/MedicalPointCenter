package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.model.HistoriaWizytDTO;
import com.przychodnia.przychodnia_aplikacja.model.LekarzDTO;
import com.przychodnia.przychodnia_aplikacja.model.SpecjalisciDTO;
import com.przychodnia.przychodnia_aplikacja.repository.LekarzRepository;
import com.przychodnia.przychodnia_aplikacja.repository.SpecjalizacjaRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecjalisciService {

    private final LekarzRepository lekarzRepository;
    private final UserRepository userRepository;
    private final SpecjalizacjaRepository specjalizacjaRepository;

    public SpecjalisciService(LekarzRepository lekarzRepository, UserRepository userRepository, SpecjalizacjaRepository specjalizacjaRepository) {
        this.lekarzRepository = lekarzRepository;
        this.userRepository = userRepository;
        this.specjalizacjaRepository = specjalizacjaRepository;
    }

    public List<SpecjalisciDTO> showSpecjalisci(){

        //pobranie listy id wszystkich lekarzy
        List<Long> idLekarze = lekarzRepository.getAllId();
        for(Long id : idLekarze){
            System.out.println(id);
        }

        //pobranie listy iduserow na podstawie id lekarzy ale AKTYWNYCH
        List<Long> idUserzy = lekarzRepository.getIdUserByIdLekarz(idLekarze);
        System.out.println("userzy");
        for(Long id : idUserzy){
            System.out.println(id);
        }

        //pobranie imion lekarzy
        List<String> imiona = userRepository.getImionaByIdUser(idUserzy);
        System.out.println("imiona");
        for(String id : imiona){
            System.out.println(id);
        }

        //pobranie nazwisk lekarzy
        List<String> nazwiska = userRepository.getNazwiskaByIdUser(idUserzy);
        System.out.println("nazwiska");
        for(String id : nazwiska){
            System.out.println(id);
        }

        //pobranie idlekarzy na podstawie iduserow
        List<Long> idLekarze_Aktywni = lekarzRepository.getIdLekarzeByIdUserzy(idUserzy);
        System.out.println("idlekarze");
        for(Long id : idLekarze_Aktywni){
            System.out.println(id);
        }

        //pobranie specjalizacji
        List<String> spec = specjalizacjaRepository.getSpecByIdLekarzyConcat(idLekarze_Aktywni);
        System.out.println("spec");
        for(String id : spec){
            System.out.println(id);
        }


        //Tworzymy liste skladajaca sie z powyzszych danych
        List<SpecjalisciDTO> specjalisciDTO = new ArrayList<>();

        for (int i = 0; i < idLekarze_Aktywni.size(); i++) {
            String imie = imiona.get(i);
            String nazwisko = nazwiska.get(i);
            String specjalizacja = spec.get(i);

            // Tworzymy nowy obiekt specDTO i dodajemy go do listy
            SpecjalisciDTO specDTO = new SpecjalisciDTO(idLekarze_Aktywni.get(i) ,imie, nazwisko, specjalizacja);
            specjalisciDTO.add(specDTO);
        }

        return specjalisciDTO;
    }
}
