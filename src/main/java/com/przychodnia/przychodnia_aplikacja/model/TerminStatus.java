package com.przychodnia.przychodnia_aplikacja.model;

import java.time.LocalTime;

public class TerminStatus {
    private LocalTime godzina;
    private boolean isReserved;
    private String imie;
    private String nazwisko;

    public TerminStatus(LocalTime godzina, boolean isReserved, String imie, String nazwisko) {
        this.godzina = godzina;
        this.isReserved = isReserved;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public LocalTime getGodzina() {
        return godzina;
    }

    public boolean isReserved() {
        return isReserved;
    }
}
