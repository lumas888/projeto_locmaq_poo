package io.github.mateus.projetopoo.repository;

import io.github.mateus.projetopoo.model.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private static final String FILE_NAME = "clientes.dat";

    public void salvar(Cliente cliente) throws IOException {
        List<Cliente> lista = listarTodos();
        lista.add(cliente);
        salvarLista(lista);
    }

    public List<Cliente> listarTodos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Cliente>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void salvarLista(List<Cliente> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lista);
        }
    }

    public void excluir(int idCliente) throws IOException {
        List<Cliente> lista = listarTodos();
        lista.removeIf(c -> c.getIdCliente() == idCliente);
        salvarLista(lista);
    }

    public void atualizar(Cliente clienteAtualizado) throws IOException {
        List<Cliente> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdCliente() == clienteAtualizado.getIdCliente()) {
                lista.set(i, clienteAtualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public Cliente buscarPorId(int idCliente) {
        for (Cliente c : listarTodos()) {
            if (c.getIdCliente() == idCliente) return c;
        }
        return null;
    }
}
