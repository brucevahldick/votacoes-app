package com.example.votacoes_app.model;

import java.io.Serializable;

public class Reuniao implements Serializable {

    private String id, conselho, data, hora, local, quorum, secretario;
    private int status;

    public Reuniao() {
    }

    public Reuniao(String conselho, String data, String hora, String local, String quorum, String secretario) {
        this.conselho = conselho;
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.quorum = quorum;
        this.secretario = secretario;
        this.status = 2;
    }

    public String getConselho() {
        return conselho;
    }

    public void setConselho(String conselho) {
        this.conselho = conselho;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getQuorum() {
        return quorum;
    }

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }

    public String getSecretario() {
        return secretario;
    }

    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
