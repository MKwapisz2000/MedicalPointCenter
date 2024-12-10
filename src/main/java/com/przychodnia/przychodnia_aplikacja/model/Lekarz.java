package com.przychodnia.przychodnia_aplikacja.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lekarz")
public class Lekarz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idlekarz;

    @OneToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false, unique = true)
    private User user;

    public Long getIdlekarz() {
        return idlekarz;
    }

    public void setIdlekarz(Long idlekarz) {
        this.idlekarz = idlekarz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}