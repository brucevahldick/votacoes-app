package com.example.votacoes_app.model;

public class Reuniao {

    private String conselho;
    private String data;
    private String hora;
    private String local;
    private String quorum;
    private String secretario;

    public Reuniao() {
    }

    public Reuniao(String conselho, String data, String hora, String local, String quorum, String secretario) {
        this.conselho = conselho;
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.quorum = quorum;
        this.secretario = secretario;
    }

    public String getConselho() {
        return conselho;
    }

    public void setConselho(String conselho) {
        this.conselho = conselho;
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
}
