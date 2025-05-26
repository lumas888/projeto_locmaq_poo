package io.github.mateus.projetopoo.model;

import java.io.Serializable;

public class BoletimMedicao implements Serializable {
    private int id;
    private String descricao;
    private String data;
    private double valorMedido;

    public BoletimMedicao(int id, String descricao, String data, double valorMedido) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.valorMedido = valorMedido;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public double getValorMedido() { return valorMedido; }
    public void setValorMedido(double valorMedido) { this.valorMedido = valorMedido; }

    @Override
    public String toString() {
        return id + " - " + descricao + " - " + data + " - Valor: " + valorMedido;
    }
}
