package com.przychodnia.przychodnia_aplikacja.model;

public class LekarzDTO {
    private Long idLekarza;
    private String imie;
    private String nazwisko;

    public LekarzDTO(Long idLekarza, String imie, String nazwisko) {
        this.idLekarza = idLekarza;
        this.imie = imie;
        this.nazwisko = nazwisko;
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
}
