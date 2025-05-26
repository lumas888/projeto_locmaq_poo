package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.Cliente;
import io.github.mateus.projetopoo.repository.ClienteRepository;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.regex.Pattern;

public class ClienteView extends VBox {
    private TextField tfId = new TextField();
    private TextField tfNome = new TextField();
    private TextField tfCnpj = new TextField();
    private TextField tfTelefone = new TextField();
    private TextField tfEmail = new TextField();
    private TextArea taLista = new TextArea();

    private ClienteRepository repo = new ClienteRepository();

    public ClienteView() {
        setSpacing(10);
        setPadding(new Insets(15));

        tfId.setPromptText("ID (para buscar, atualizar ou excluir)");
        tfNome.setPromptText("Nome");
        tfCnpj.setPromptText("CNPJ");
        tfTelefone.setPromptText("Telefone");
        tfEmail.setPromptText("Email");
        taLista.setPrefRowCount(10);
        taLista.setEditable(false);

        Button btnSalvar = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnListar = new Button("Listar Todos");

        btnSalvar.setOnAction(e -> inserirCliente());
        btnBuscar.setOnAction(e -> buscarCliente());
        btnAtualizar.setOnAction(e -> atualizarCliente());
        btnExcluir.setOnAction(e -> excluirCliente());
        btnListar.setOnAction(e -> listarClientes());

        getChildren().addAll(
                new HBox(10, tfId, btnBuscar, btnAtualizar, btnExcluir),
                tfNome, tfCnpj, tfTelefone, tfEmail,
                new HBox(10, btnSalvar, btnListar),
                taLista
        );
    }

    private boolean emailValido(String email) {
        String regex = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$";
        return Pattern.matches(regex, email);
    }

    private boolean cnpjValido(String cnpj) {
        String regex = "^\\d{14}$";
        return Pattern.matches(regex, cnpj);
    }

    private void inserirCliente() {
        try {
            if (tfNome.getText().isEmpty() || tfCnpj.getText().isEmpty() ||
                    tfTelefone.getText().isEmpty() || tfEmail.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            if (!emailValido(tfEmail.getText())) {
                taLista.setText("E-mail inválido.");
                return;
            }
            if (!cnpjValido(tfCnpj.getText())) {
                taLista.setText("CNPJ deve ter 14 dígitos numéricos.");
                return;
            }
            if (!repo.cnpjDisponivel(tfCnpj.getText(), null)) {
                taLista.setText("CNPJ já cadastrado.");
                return;
            }
            Cliente cliente = new Cliente(
                    tfNome.getText(),
                    tfCnpj.getText(),
                    tfTelefone.getText(),
                    tfEmail.getText()
            );
            repo.salvar(cliente);
            limparCampos();
            listarClientes();
        } catch (Exception ex) {
            taLista.setText("Erro ao inserir: " + ex.getMessage());
        }
    }

    private void buscarCliente() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para buscar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            Cliente cliente = repo.buscarPorId(id);
            if (cliente != null) {
                tfNome.setText(cliente.getNome());
                tfCnpj.setText(cliente.getCnpj());
                tfTelefone.setText(cliente.getTelefone());
                tfEmail.setText(cliente.getEmail());
            } else {
                taLista.setText("Cliente não encontrado.");
            }
        } catch (Exception e) {
            taLista.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void atualizarCliente() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para atualizar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            if (tfNome.getText().isEmpty() || tfCnpj.getText().isEmpty() ||
                    tfTelefone.getText().isEmpty() || tfEmail.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            if (!emailValido(tfEmail.getText())) {
                taLista.setText("E-mail inválido.");
                return;
            }
            if (!cnpjValido(tfCnpj.getText())) {
                taLista.setText("CNPJ deve ter 14 dígitos numéricos.");
                return;
            }
            if (!repo.cnpjDisponivel(tfCnpj.getText(), id)) {
                taLista.setText("CNPJ já cadastrado para outro cliente.");
                return;
            }
            Cliente cliente = new Cliente(
                    tfNome.getText(),
                    tfCnpj.getText(),
                    tfTelefone.getText(),
                    tfEmail.getText()
            );
            cliente.setIdCliente(id);
            repo.atualizar(cliente);
            limparCampos();
            listarClientes();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirCliente() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para excluir.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            repo.excluir(id);
            limparCampos();
            listarClientes();
        } catch (IOException ex) {
            taLista.setText("Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarClientes() {
        taLista.clear();
        for (Cliente c : repo.listarTodos()) {
            taLista.appendText(c.toString() + "\n");
        }
    }

    private void limparCampos() {
        tfId.clear();
        tfNome.clear();
        tfCnpj.clear();
        tfTelefone.clear();
        tfEmail.clear();
    }
}