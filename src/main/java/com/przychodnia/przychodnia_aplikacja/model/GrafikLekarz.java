package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "grafik_lekarz")
public class GrafikLekarz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Jeden lekarz może mieć wiele grafików
    @ManyToOne
    @JoinColumn(name = "idlekarz", referencedColumnName = "idlekarz", nullable = false)
    private Lekarz lekarz;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime godzina_startowa;

    @Column(nullable = false)
    private LocalTime godzina_koncowa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lekarz getLekarz() {
        return lekarz;
    }

    public void setLekarz(Lekarz lekarz) {
        this.lekarz = lekarz;
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
