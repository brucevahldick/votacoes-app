package com.example.votacoes_app.model;

public class Integrante {

    private String cpf;
    private String nome;
    private String conselho;
    private String contato;
    private String senha;

    public Integrante(String cpf, String nome, String conselho, String contato, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.conselho = conselho;
        this.contato = contato;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Integrante{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", conselho='" + conselho + '\'' +
                ", contato='" + contato + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
