package com.przychodnia.przychodnia_aplikacja.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class HistoriaWizytDTO {
    private String imieLekarz;
    private String nazwiskoLekarz;
    private LocalDate data;
    private LocalTime godzina;
    private String status;

    public HistoriaWizytDTO(String imieLekarz, String nazwiskoLekarz, LocalDate data, LocalTime godzina, String status) {
        this.imieLekarz = imieLekarz;
        this.nazwiskoLekarz = nazwiskoLekarz;
        this.data = data;
        this.godzina = godzina;
        this.status = status;
    }

    public HistoriaWizytDTO(String status, LocalDate data) {
        this.data = data;
        this.status = status;
    }

    public String getImieLekarz() {
        return imieLekarz;
    }

    public void setImieLekarz(String imieLekarz) {
        this.imieLekarz = imieLekarz;
    }

    public String getNazwiskoLekarz() {
        return nazwiskoLekarz;
    }

    public void setNazwiskoLekarz(String nazwiskoLekarz) {
        this.nazwiskoLekarz = nazwiskoLekarz;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getGodzina() {
        return godzina;
    }

    public void setGodzina(LocalTime godzina) {
        this.godzina = godzina;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
