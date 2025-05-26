package io.github.mateus.projetopoo.model;

import java.io.Serializable;

public class Equipamento implements Serializable {
    private int idEquipamento;
    private String nome;
    private String descricao;
    private String fabricante;
    private String numeroSerie;

    public Equipamento(String nome, String descricao, String fabricante, String numeroSerie) {
        this.nome = nome;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.numeroSerie = numeroSerie;
    }

    public int getIdEquipamento() { return idEquipamento; }
    public void setIdEquipamento(int idEquipamento) { this.idEquipamento = idEquipamento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    @Override
    public String toString() {
        return idEquipamento + " - " + nome + " - " + descricao + " - " + fabricante + " - " + numeroSerie;
    }
}