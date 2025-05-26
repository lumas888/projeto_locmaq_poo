package io.github.mateus.projetopoo.model;

import java.io.Serializable;

public class ContratoLocacao implements Serializable {
    private int id;
    private String dataInicio;
    private String dataFim;
    private String descricao;
    private double valor;

    public ContratoLocacao(int id, String dataInicio, String dataFim, String descricao, double valor) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    @Override
    public String toString() {
        return id + " - " + descricao + " - De: " + dataInicio + " até " + dataFim + " - Valor: " + valor;
    }
}
