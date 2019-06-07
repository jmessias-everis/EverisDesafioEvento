package com.everis.everisdesafioevento.Domain;

import java.io.Serializable;

public class Registro implements Serializable {
    private long id;
    private long idParticipante;
    private long idEvento;

    public Registro(long idParticipante, long idEvento) {
        this.idParticipante = idParticipante;
        this.idEvento = idEvento;
    }

    public Registro(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
    }
}


