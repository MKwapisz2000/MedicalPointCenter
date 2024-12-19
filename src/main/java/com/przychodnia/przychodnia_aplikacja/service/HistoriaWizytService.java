package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.model.HistoriaWizytDTO;
import com.przychodnia.przychodnia_aplikacja.model.LekarzDTO;
import com.przychodnia.przychodnia_aplikacja.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<HistoriaWizytDTO> getHistory(String email) {

        System.out.println("Email: " + email);

        //id pacjenta
        Long idUser = userRepository.getIDByEmail(email);
        Long idPacjent = pacjentRepository.getIdPacjentByIdUser(idUser);
        System.out.println(idPacjent);

        LocalDate dzisiaj = LocalDate.now();
        LocalTime teraz = LocalTime.now();

        //sprawdzenie czy pacjent ma jakiekolwiek wizyty
        if(!(wizytaRepository.existsByIdPacjent(idPacjent))){
            throw new RuntimeException("Brak wizyt do wyświetlenia");
        }

        //Lista id grafików lekarzy danego pacjenta na podstawie idpacjenta
        List<Long> idGrafiki = wizytaRepository.getIdGrafikByIdPacjent(idPacjent);
        System.out.println("id grafiki:");
        for(Long id : idGrafiki){
            System.out.println(id);
        }

        //Lista idlekarzy na podstawie idgrafiku
        List<Long> idLekarzy = new ArrayList<>();
        for(Long id : idGrafiki){
            idLekarzy.add(grafikLekarzRepository.getIdLekrazbyId(id));
        }
        System.out.println("id lekarze:");
        for(Long id : idLekarzy){
            System.out.println(id);
        }

        //lista imion lekarzy na podstawie idlekarzy
        List<String> imiona = new ArrayList<>();
        for(Long id : idLekarzy){
            imiona.add(userRepository.getImieByIdLekarz(id));
        }
        System.out.println("imiona:");
        for(String id : imiona){
            System.out.println(id);
        }

        //lista nazwisk lekarzy na podstawie idlekarzy
        List<String> nazwiska = new ArrayList<>();
        for(Long id : idLekarzy){
            nazwiska.add(userRepository.getNazwiskoByIdLekarz(id));
        }
        System.out.println("nazwiska:");
        for(String id : nazwiska){
            System.out.println(id);
        }

        //lista dat na podstawie idgrafików
        List<LocalDate> daty = new ArrayList<>();
        for(Long id : idGrafiki){
            daty.add(grafikLekarzRepository.getDataById(id));
        }
        System.out.println("daty:");
        for(LocalDate id : daty){
            System.out.println(id);
        }

        //godziny na podstawie idpacjenta
        List<LocalTime> godziny = wizytaRepository.getGodzinaByIdPacjent(idPacjent);
        System.out.println("godziny");
        for(LocalTime id : godziny){
            System.out.println(id);
        }

        //status wizyty na podstawie idpacjenta
        List<String> statusy = wizytaRepository.getStatusyByIdPacjent(idPacjent);
        System.out.println("statusy");
        for(String id : statusy){
            System.out.println(id);
        }


        //Tworzymy liste skladajaca sie z powyzszych danych
        List<HistoriaWizytDTO> historiaDTO = new ArrayList<>();

        for (int i = 0; i < idGrafiki.size(); i++) {
            String imie_lekarz = imiona.get(i);
            String nazwisko_lekarz = nazwiska.get(i);
            LocalDate data_wizyty = daty.get(i);
            LocalTime godzina_wizyty = godziny.get(i);
            String status = statusy.get(i);

            //sprawdzamy czy wizyta jest czasu minionego
            if (data_wizyty.isBefore(dzisiaj) || (data_wizyty.isEqual(dzisiaj) && godzina_wizyty.isBefore(teraz))) {
                status = "ODBYTA";

            }else if (!status.equalsIgnoreCase("ODBYTA")) {
                status = "ZAREZERWOWANA";
            }
            // Aktualizujemy status wizyty w bazie danych
            Long wizytaId = wizytaRepository.getWizytaIdByPacjentAndGrafik(idPacjent, idGrafiki.get(i), godzina_wizyty);
            wizytaRepository.updateStatusById(wizytaId, status);

            // Tworzymy nowy obiekt historiaDTO i dodajemy go do listy
            HistoriaWizytDTO historiaWizytDTO = new HistoriaWizytDTO(imie_lekarz, nazwisko_lekarz, data_wizyty, godzina_wizyty, status);
            historiaDTO.add(historiaWizytDTO);
            System.out.println("----------------------------");
            System.out.print(historiaWizytDTO.getStatus() +" ");
            System.out.print(historiaWizytDTO.getData()+" ");
            System.out.print(historiaWizytDTO.getGodzina()+" ");
            System.out.print(historiaWizytDTO.getImieLekarz()+" ");
            System.out.print(historiaWizytDTO.getNazwiskoLekarz()+" ");
            System.out.println();

        }


        return historiaDTO;
    }
}

