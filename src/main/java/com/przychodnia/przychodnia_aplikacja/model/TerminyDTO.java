package com.przychodnia.przychodnia_aplikacja.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TerminyDTO {

    private LocalDate data;
    private LocalTime godzina_startowa;
    private LocalTime godzina_koncowa;
    private List<LocalTime> dostepneGodziny;

    public TerminyDTO(LocalDate data, LocalTime godzina_startowa, LocalTime godzina_koncowa) {
        this.data = data;
        this.godzina_startowa = godzina_startowa;
        this.godzina_koncowa = godzina_koncowa;
    }


    public List<LocalTime> getDostepneGodziny() {
        return dostepneGodziny;
    }

    public void setDostepneGodziny(List<LocalTime> dostepneGodziny) {
        this.dostepneGodziny = dostepneGodziny;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getGodzina_startowa() {
        return godzina_startowa;
    }

    public void setGodzina_startowa(LocalTime godzina_startowa) {
        this.godzina_startowa = godzina_startowa;
    }

    public LocalTime getGodzina_koncowa() {
        return godzina_koncowa;
    }

    public void setGodzina_koncowa(LocalTime godzina_koncowa) {
        this.godzina_koncowa = godzina_koncowa;
    }
}
