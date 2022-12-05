package com.example.votacoes_app.model;

public class ItemPauta {

    private String id, processo, status, idReuniao;
    private int favoravel, taxa_aprovacao;

    public ItemPauta(String processo, int taxa_aprovacao, String idReuniao) {
        this.processo = processo;
        this.status = "Pendente";
        this.idReuniao = idReuniao;
        this.favoravel = 0;
        this.taxa_aprovacao = taxa_aprovacao;
    }

    public ItemPauta() {
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFavoravel() {
        return favoravel;
    }

    public void setFavoravel(int favoravel) {
        this.favoravel = favoravel;
    }

    public int getTaxa_aprovacao() {
        return taxa_aprovacao;
    }

    public void setTaxa_aprovacao(int taxa_aprovacao) {
        this.taxa_aprovacao = taxa_aprovacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addVotoFavoravel(){
        this.favoravel += 1;
    }

    public void addVotoContrario(){
        this.favoravel -= 1;
    }

    public String getIdReuniao() {
        return idReuniao;
    }

    public void setIdReuniao(String idReuniao) {
        this.idReuniao = idReuniao;
    }
}
