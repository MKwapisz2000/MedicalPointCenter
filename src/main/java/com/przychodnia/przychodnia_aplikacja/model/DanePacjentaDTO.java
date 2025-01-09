package com.przychodnia.przychodnia_aplikacja.model;

public class DanePacjentaDTO {
    private String imie;
    private String nazwisko;
    private String pesel;
    private String dataUrodzenia;
    private String numerTelefonu;
    private String email;
    private String miasto;
    private String ulica;
    private String nrBudynku;
    private String nrLokalu;
    private String kodPocztowy;
    private Long idPacjent;
    public DanePacjentaDTO(){
    }

    public DanePacjentaDTO(String imie, String nazwisko, String pesel, String dataUrodzenia, String numerTelefonu, String email, String miasto, String ulica, String nrBudynku, String nrLokalu, String kodPocztowy) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.dataUrodzenia = dataUrodzenia;
        this.numerTelefonu = numerTelefonu;
        this.email = email;
        this.miasto = miasto;
        this.ulica = ulica;
        this.nrBudynku = nrBudynku;
        this.nrLokalu = nrLokalu;
        this.kodPocztowy = kodPocztowy;
    }

    public Long getIdPacjent() {
        return idPacjent;
    }

    public void setIdPacjent(Long idPacjent) {
        this.idPacjent = idPacjent;
    }

    public DanePacjentaDTO(Long idPacjent, String imie, String nazwisko) {
        this.idPacjent = idPacjent;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public DanePacjentaDTO(String imie, String nazwisko, String pesel, String dataUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.dataUrodzenia = dataUrodzenia;
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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(String dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    public void setNumerTelefonu(String numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNrBudynku() {
        return nrBudynku;
    }

    public void setNrBudynku(String nrBudynku) {
        this.nrBudynku = nrBudynku;
    }

    public String getNrLokalu() {
        return nrLokalu;
    }

    public void setNrLokalu(String nrLokalu) {
        this.nrLokalu = nrLokalu;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }
}
