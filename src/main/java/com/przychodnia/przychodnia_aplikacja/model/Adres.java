package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;

@Entity
@Table(name = "adres")
public class Adres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idadres;

    @OneToOne
    @JoinColumn(name = "idpacjent", referencedColumnName = "idpacjent", nullable = false, unique = true)
    private Pacjent pacjent;

    @Column(nullable = false)
    private String miasto;

    @Column(nullable = false)
    private String ulica;

    @Column(nullable = false)
    private String nrBudynku;

    private String nrLokalu;

    @Column(nullable = false)
    private String kodPocztowy;

    public Long getIdadres() {
        return idadres;
    }

    public void setIdadres(Long idadres) {
        this.idadres = idadres;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public void setPacjent(Pacjent pacjent) {
        this.pacjent = pacjent;
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
