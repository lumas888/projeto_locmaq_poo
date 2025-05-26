package io.github.mateus.projetopoo.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int idUsuario;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private TipoUsuario tipo;

    public Usuario(String nome, String login, String senha, String email, TipoUsuario tipo) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.tipo = tipo;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return idUsuario + " - " + nome + " - " + login + " - " + tipo + " - " + email;
    }
}
