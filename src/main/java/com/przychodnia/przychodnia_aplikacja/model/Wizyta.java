package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "wizyta")
public class Wizyta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idwizyta;

    //Jeden pacjent ma wiele wizyt
    @ManyToOne
    @JoinColumn(name = "idpacjent", referencedColumnName = "idpacjent", nullable = false)
    private Pacjent pacjent;

    //Jeden pacjent ma wiele wizyt
    @ManyToOne
    @JoinColumn(name = "idgrafik", referencedColumnName = "id", nullable = false)
    private GrafikLekarz grafik;

    @Enumerated(EnumType.STRING)
    @Column()
    private Status status;

    @Column(nullable = false)
    private LocalTime godzina;

    public enum Status {
        ZAREZERWOWANA, ODBYTA
    }

    public Long getIdwizyta() {
        return idwizyta;
    }

    public void setIdwizyta(Long idwizyta) {
        this.idwizyta = idwizyta;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public void setPacjent(Pacjent pacjent) {
        this.pacjent = pacjent;
    }

    public GrafikLekarz getGrafik() {
        return grafik;
    }

    public void setGrafik(GrafikLekarz grafik) {
        this.grafik = grafik;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalTime getGodzina() {
        return godzina;
    }

    public void setGodzina(LocalTime godzina) {
        this.godzina = godzina;
    }
}
