package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iduser;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String nazwisko;

    @Column(nullable = false, unique = true)
    @Email(message = "Email nie jest poprawny")
    private String email;

    @Column(nullable = false)
    @Size(min = 8, message = "Hasło musi mieć co najmniej 8 znaków")
    private String haslo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypUzytkownika typ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum TypUzytkownika {
        ADMIN, LEKARZ, PACJENT
    }

    public enum Status {
        AKTYWNY, NIEAKTYWNY
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public TypUzytkownika getTyp() {
        return typ;
    }

    public void setTyp(TypUzytkownika typ) {
        this.typ = typ;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
