package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;

@Entity
@Table(name = "specjalizacja")
public class Specjalizacja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idspecjalizacja;

    //Jeden lekarz może mieć wiele specjalizacji
    @ManyToOne
    @JoinColumn(name = "idlekarz", referencedColumnName = "idlekarz", nullable = false)
    private Lekarz lekarz;

    @Column(nullable = false)
    private String spec;

    public Long getIdspecjalizacja() {
        return idspecjalizacja;
    }

    public void setIdspecjalizacja(Long idspecjalizacja) {
        this.idspecjalizacja = idspecjalizacja;
    }

    public Lekarz getLekarz() {
        return lekarz;
    }

    public void setLekarz(Lekarz lekarz) {
        this.lekarz = lekarz;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
