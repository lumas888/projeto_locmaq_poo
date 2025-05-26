package io.github.mateus.projetopoo.repository;

import io.github.mateus.projetopoo.model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static final String FILE_NAME = "usuarios.dat";

    public void salvar(Usuario usuario) throws IOException {
        List<Usuario> lista = listarTodos();
        // Gera novo ID automaticamente se for zero
        if (usuario.getIdUsuario() == 0) {
            int maxId = lista.stream()
                    .mapToInt(Usuario::getIdUsuario)
                    .max()
                    .orElse(0);
            usuario.setIdUsuario(maxId + 1);
        }
        lista.add(usuario);
        salvarLista(lista);
    }

    public List<Usuario> listarTodos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Usuario>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<Usuario> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lista);
        }
    }

    public void excluir(int idUsuario) throws IOException {
        List<Usuario> lista = listarTodos();
        lista.removeIf(u -> u.getIdUsuario() == idUsuario);
        salvarLista(lista);
    }

    public void atualizar(Usuario usuarioAtualizado) throws IOException {
        List<Usuario> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdUsuario() == usuarioAtualizado.getIdUsuario()) {
                lista.set(i, usuarioAtualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public Usuario buscarPorId(int idUsuario) {
        for (Usuario u : listarTodos()) {
            if (u.getIdUsuario() == idUsuario) return u;
        }
        return null;
    }

    // Método para autenticação por login e senha
    public Usuario autenticar(String login, String senha) {
        for (Usuario u : listarTodos()) {
            if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }
}