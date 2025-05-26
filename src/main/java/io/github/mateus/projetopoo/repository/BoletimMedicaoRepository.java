package io.github.mateus.projetopoo.repository;

import io.github.mateus.projetopoo.model.BoletimMedicao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BoletimMedicaoRepository {
    private static final String FILE_NAME = "boletins.dat";

    public void salvar(BoletimMedicao boletim) throws IOException {
        List<BoletimMedicao> lista = listarTodos();
        int novoId = lista.stream().mapToInt(BoletimMedicao::getId).max().orElse(0) + 1;
        boletim.setId(novoId);
        lista.add(boletim);
        salvarLista(lista);
    }

    public List<BoletimMedicao> listarTodos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<BoletimMedicao>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<BoletimMedicao> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lista);
        }
    }

    public void excluir(int id) throws IOException {
        List<BoletimMedicao> lista = listarTodos();
        lista.removeIf(b -> b.getId() == id);
        salvarLista(lista);
    }

    public void atualizar(BoletimMedicao boletimAtualizado) throws IOException {
        List<BoletimMedicao> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == boletimAtualizado.getId()) {
                lista.set(i, boletimAtualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public BoletimMedicao buscarPorId(int id) {
        for (BoletimMedicao b : listarTodos()) {
            if (b.getId() == id) return b;
        }
        return null;
    }
}