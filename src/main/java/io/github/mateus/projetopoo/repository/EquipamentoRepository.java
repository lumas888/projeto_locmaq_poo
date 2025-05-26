package io.github.mateus.projetopoo.repository;

import io.github.mateus.projetopoo.model.Equipamento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoRepository {
    private static final String FILE_NAME = "equipamentos.dat";

    public void salvar(Equipamento equipamento) throws IOException {
        List<Equipamento> lista = listarTodos();
        lista.add(equipamento);
        salvarLista(lista);
    }

    public List<Equipamento> listarTodos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Equipamento>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<Equipamento> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lista);
        }
    }

    public void excluir(int idEquipamento) throws IOException {
        List<Equipamento> lista = listarTodos();
        lista.removeIf(e -> e.getIdEquipamento() == idEquipamento);
        salvarLista(lista);
    }

    public void atualizar(Equipamento equipamentoAtualizado) throws IOException {
        List<Equipamento> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdEquipamento() == equipamentoAtualizado.getIdEquipamento()) {
                lista.set(i, equipamentoAtualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public Equipamento buscarPorId(int idEquipamento) {
        for (Equipamento e : listarTodos()) {
            if (e.getIdEquipamento() == idEquipamento) return e;
        }
        return null;
    }
}

