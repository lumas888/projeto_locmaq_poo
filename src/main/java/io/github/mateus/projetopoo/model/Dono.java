package io.github.mateus.projetopoo.model;

import java.io.Serializable;

public class Dono implements Serializable {
    private int idDono;
    private String nome;
    private String endereco;
    private String email;
    private String telefone;
    private String banco;
    private String agencia;
    private String numeroConta;

    public Dono(int idDono, String nome, String endereco, String email, String telefone, String banco, String agencia, String numeroConta) {
        this.idDono = idDono;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.banco = banco;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
    }

    public int getIdDono() { return idDono; }
    public void setIdDono(int idDono) { this.idDono = idDono; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    @Override
    public String toString() {
        return idDono + " - " + nome + " - " + banco + " - " + agencia + " - " + numeroConta;
    }
}
