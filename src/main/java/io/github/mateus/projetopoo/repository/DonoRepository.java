package io.github.mateus.projetopoo.repository;

import io.github.mateus.projetopoo.model.Dono;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DonoRepository {
    private static final String FILE_NAME = "donos.dat";

    public void salvar(Dono dono) throws IOException {
        List<Dono> lista = listarTodos();
        int novoId = lista.stream().mapToInt(Dono::getIdDono).max().orElse(0) + 1;
        dono.setIdDono(novoId);
        lista.add(dono);
        salvarLista(lista);
    }

    public List<Dono> listarTodos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Dono>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<Dono> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lista);
        }
    }

    public void excluir(int idDono) throws IOException {
        List<Dono> lista = listarTodos();
        lista.removeIf(d -> d.getIdDono() == idDono);
        salvarLista(lista);
    }

    public void atualizar(Dono donoAtualizado) throws IOException {
        List<Dono> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdDono() == donoAtualizado.getIdDono()) {
                lista.set(i, donoAtualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public Dono buscarPorId(int idDono) {
        for (Dono d : listarTodos()) {
            if (d.getIdDono() == idDono) return d;
        }
        return null;
    }

    public boolean emailDisponivel(String email, Integer ignorarId) {
        for (Dono d : listarTodos()) {
            if (d.getEmail().equalsIgnoreCase(email) && (ignorarId == null || d.getIdDono() != ignorarId)) {
                return false;
            }
        }
        return true;
    }
}