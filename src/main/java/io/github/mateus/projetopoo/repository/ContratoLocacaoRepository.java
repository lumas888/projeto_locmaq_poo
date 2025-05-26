package io.github.mateus.projetopoo.repository;

import io.github.mateus.projetopoo.model.ContratoLocacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoLocacaoRepository {
    private static final String FILE_NAME = "contratos.dat";

    public void salvar(ContratoLocacao contrato) throws IOException {
        List<ContratoLocacao> lista = listarTodos();
        lista.add(contrato);
        salvarLista(lista);
    }

    public List<ContratoLocacao> listarTodos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<ContratoLocacao>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<ContratoLocacao> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lista);
        }
    }

    public void excluir(int id) throws IOException {
        List<ContratoLocacao> lista = listarTodos();
        lista.removeIf(c -> c.getId() == id);
        salvarLista(lista);
    }

    public void atualizar(ContratoLocacao contratoAtualizado) throws IOException {
        List<ContratoLocacao> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == contratoAtualizado.getId()) {
                lista.set(i, contratoAtualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public ContratoLocacao buscarPorId(int id) {
        for (ContratoLocacao c : listarTodos()) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}
