package io.github.mateus.projetopoo.model;

import java.io.Serializable;

public class ContratoLocacao implements Serializable {
    private int id;
    private String dataInicio;
    private String dataFim;
    private String descricao;
    private double valor;
    private Cliente cliente;
    private Equipamento equipamento;

    public ContratoLocacao(String dataInicio, String dataFim, String descricao, double valor, Cliente cliente, Equipamento equipamento) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.valor = valor;
        this.cliente = cliente;
        this.equipamento = equipamento;
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

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Equipamento getEquipamento() { return equipamento; }
    public void setEquipamento(Equipamento equipamento) { this.equipamento = equipamento; }

    @Override
    public String toString() {
        return id + " - " + descricao + " - De: " + dataInicio + " até " + dataFim + " - Valor: " + valor +
               " | Cliente: " + (cliente != null ? cliente.getNome() : "") +
               " | Equipamento: " + (equipamento != null ? equipamento.getNome() : "");
    }
}

