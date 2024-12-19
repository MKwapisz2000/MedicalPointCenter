package com.przychodnia.przychodnia_aplikacja.model;

public class SpecjalisciDTO {
    private Long idLekarza;
    private String imie;
    private String nazwisko;
    private String specjalizacja;

    public SpecjalisciDTO(Long idLekarza, String imie, String nazwisko, String specjalizacja) {
        this.idLekarza = idLekarza;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.specjalizacja = specjalizacja;
    }

    public Long getIdLekarza() {
        return idLekarza;
    }

    public void setIdLekarza(Long idLekarza) {
        this.idLekarza = idLekarza;
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

    public String getSpecjalizacja() {
        return specjalizacja;
    }

    public void setSpecjalizacja(String specjalizacja) {
        this.specjalizacja = specjalizacja;
    }
}
