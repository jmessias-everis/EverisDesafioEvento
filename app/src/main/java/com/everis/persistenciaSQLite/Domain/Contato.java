package com.everis.persistenciaSQLite.Domain;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;

public class Contato implements Serializable, Comparable<Contato>{
    private long id;
    private String nome;
    private String telefone;
    private boolean ativo;

    public Contato(){}

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int compareTo(Contato o) {
        return getTelefone().compareTo(o.getTelefone());
    }

}