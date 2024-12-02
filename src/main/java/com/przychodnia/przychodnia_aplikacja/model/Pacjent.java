package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacjent")
public class Pacjent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpacjent;

    @OneToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false, unique = true)
    private User user;


    @Column(nullable = false, unique = true, length = 11)
    private String pesel;

    @Column(name = "data_urodzenia", nullable = false)
    private LocalDate dataUrodzenia;

    @Column(name = "numer_tel", nullable = false, length = 9)
    private String numerTel;

    public Long getIdpacjent() {
        return idpacjent;
    }

    public void setIdpacjent(Long idpacjent) {
        this.idpacjent = idpacjent;
    }

    public User getIduser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIduser(User iduser) {
        this.user = iduser;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(LocalDate dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getNumerTel() {
        return numerTel;
    }

    public void setNumerTel(String numerTel) {
        this.numerTel = numerTel;
    }
}
