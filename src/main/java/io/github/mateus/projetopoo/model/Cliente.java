package io.github.mateus.projetopoo.model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int idCliente;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;

    public Cliente(String nome, String cnpj, String telefone, String email) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return idCliente + " - " + nome + " - " + cnpj + " - " + telefone + " - " + email;
    }
}