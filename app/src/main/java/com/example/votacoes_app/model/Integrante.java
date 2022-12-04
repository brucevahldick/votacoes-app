package com.example.votacoes_app.model;

import java.io.Serializable;

public class Integrante implements Serializable {

    private String id, imgageId, userId, cpf, nome, conselho, email, contato,senha;
    private int tipo;

    public Integrante() {
    }

    /*
    public Integrante(String cpf, String nome, String conselho, String contato, int tipo) {
        this.cpf = cpf;
        this.nome = nome;
        this.conselho = conselho;
        this.contato = contato;
        this.tipo = tipo;
    }
    */
    public Integrante(String cpf, String nome, String conselho, String email, String contato, int tipo, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.conselho = conselho;
        this.email = email;
        this.contato = contato;
        this.tipo = tipo;
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
