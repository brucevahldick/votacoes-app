package com.example.votacoes_app.model;

public class Integrante {

    private String imgageId;

    private String cpf;
    private String nome;
    private String conselho;
    private String contato;
    private int tipo;
    private String senha;

    public Integrante() {
    }

    public Integrante(String cpf, String nome, String conselho, String contato, int tipo, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.conselho = conselho;
        this.contato = contato;
        this.tipo = tipo;
        this.senha = senha;
    }

    public String getImgageId() {
        return imgageId;
    }

    public void setImgageId(String imgageId) {
        this.imgageId = imgageId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConselho() {
        return conselho;
    }

    public void setConselho(String conselho) {
        this.conselho = conselho;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
