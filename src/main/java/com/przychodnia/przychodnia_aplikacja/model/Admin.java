package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idadmin;

    @OneToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false, unique = true)
    private User user;

    public Long getIdadmin() {
        return idadmin;
    }

    public void setIdadmin(Long idadmin) {
        this.idadmin = idadmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}