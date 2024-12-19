package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.model.DanePacjentaDTO;
import com.przychodnia.przychodnia_aplikacja.model.HistoriaWizytDTO;
import com.przychodnia.przychodnia_aplikacja.repository.GrafikLekarzRepository;
import com.przychodnia.przychodnia_aplikacja.repository.PacjentRepository;
import com.przychodnia.przychodnia_aplikacja.repository.UserRepository;
import com.przychodnia.przychodnia_aplikacja.repository.WizytaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoriaWizytLekarzService {

    private final GrafikLekarzRepository grafikLekarzRepository;
    private final WizytaRepository wizytaRepository;
    private final UserRepository userRepository;
    private final PacjentRepository pacjentRepository;

    public HistoriaWizytLekarzService(GrafikLekarzRepository grafikLekarzRepository, WizytaRepository wizytaRepository, UserRepository userRepository, PacjentRepository pacjentRepository) {
        this.grafikLekarzRepository = grafikLekarzRepository;
        this.wizytaRepository = wizytaRepository;
        this.userRepository = userRepository;
        this.pacjentRepository = pacjentRepository;
    }

    public List<DanePacjentaDTO> showPacjenci(Long idLekarz){

        List<Long> idGarfiki = grafikLekarzRepository.getIdGrafikiByIdLekarz(idLekarz);
        if(idGarfiki.isEmpty()){
            throw new RuntimeException("Brak pacjentów");
        }
        System.out.println("idgrafiki");
        for(Long id : idGarfiki){
            System.out.println(id);
        }

        //lista pacjentow leczonych przez lekarza,
        List<Long> idPacjenci = wizytaRepository.getIdPacjentByIdGrafiki(idGarfiki);
        if(idPacjenci.isEmpty()){
            throw new RuntimeException("Brak pacjentów");
        }
        System.out.println("idpacjenci");
        for(Long id : idPacjenci){
            System.out.println(id);
        }

        List<Long> idUserzy = pacjentRepository.getIdUserzyByIdPacjenci(idPacjenci);
        List<String> imiona = userRepository.getImionaByIdUser(idUserzy);
        List<String> nazwiska = userRepository.getNazwiskaByIdUser(idUserzy);

        List<DanePacjentaDTO> pacjenciDTO = new ArrayList<>();

        for (int i = 0; i < idUserzy.size(); i++) {
            String imie = imiona.get(i);    // Pobieramy odpowiednie imię
            String nazwisko = nazwiska.get(i);  // Pobieramy odpowiednie nazwisko

            // Tworzymy nowy obiekt LekarzDTO i dodajemy go do listy
            DanePacjentaDTO pacjentDTO = new DanePacjentaDTO(idUserzy.get(i), imie, nazwisko);
            pacjenciDTO.add(pacjentDTO);
            System.out.println(pacjentDTO.getImie());
            System.out.println(pacjentDTO.getNazwisko());
            System.out.println("----------");
        }

        return pacjenciDTO;
    }

    public List<LocalDate> showDaty(Long iduser, Long idLekarz){

        LocalDate dzisiaj = LocalDate.now();

        Long idpacjent = pacjentRepository.getIdPacjentByIdUser(iduser);

        //sprawdzenie czy pacjent ma jakiekolwiek wizyty
        if(!(wizytaRepository.existsByIdPacjent(idpacjent))){
            throw new RuntimeException("Brak wizyt do wyświetlenia");
        }

        //Lista id grafików na podstawie idpacjenta
        List<Long> idGrafiki = wizytaRepository.getIdGrafikByIdPacjent(idpacjent);
        System.out.println("id grafiki:");
        for(Long id : idGrafiki){
            System.out.println(id);
        }

        //lista dat
        List<LocalDate> daty = grafikLekarzRepository.getDataByIdIdLekarzData(idGrafiki, idLekarz, dzisiaj);

        return daty;
    }
}
