package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.Dono;
import io.github.mateus.projetopoo.repository.DonoRepository;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.io.IOException;
import java.util.regex.Pattern;

public class DonoView extends VBox {
    private TextField tfId = new TextField();
    private TextField tfNome = new TextField();
    private TextField tfEndereco = new TextField();
    private TextField tfEmail = new TextField();
    private TextField tfTelefone = new TextField();
    private TextField tfBanco = new TextField();
    private TextField tfAgencia = new TextField();
    private TextField tfNumeroConta = new TextField();
    private TextArea taLista = new TextArea();

    private DonoRepository repo = new DonoRepository();

    public DonoView() {
        setSpacing(10);
        setPadding(new Insets(15));

        tfId.setPromptText("ID (para buscar, atualizar ou excluir)");
        tfNome.setPromptText("Nome");
        tfEndereco.setPromptText("Endereço");
        tfEmail.setPromptText("Email");
        tfTelefone.setPromptText("Telefone");
        tfBanco.setPromptText("Banco");
        tfAgencia.setPromptText("Agência");
        tfNumeroConta.setPromptText("Número da Conta");

        taLista.setPrefRowCount(10);
        taLista.setEditable(false);

        Button btnSalvar = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnListar = new Button("Listar Todos");

        btnSalvar.setOnAction(e -> inserirDono());
        btnBuscar.setOnAction(e -> buscarDono());
        btnAtualizar.setOnAction(e -> atualizarDono());
        btnExcluir.setOnAction(e -> excluirDono());
        btnListar.setOnAction(e -> listarDonos());

        getChildren().addAll(
                new HBox(10, tfId, btnBuscar, btnAtualizar, btnExcluir),
                tfNome, tfEndereco, tfEmail, tfTelefone, tfBanco, tfAgencia, tfNumeroConta,
                new HBox(10, btnSalvar, btnListar),
                taLista
        );
    }

    private boolean emailValido(String email) {
        String regex = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$";
        return Pattern.matches(regex, email);
    }

    private void inserirDono() {
        try {
            if (tfNome.getText().isEmpty() || tfEndereco.getText().isEmpty() ||
                    tfEmail.getText().isEmpty() || tfTelefone.getText().isEmpty() ||
                    tfBanco.getText().isEmpty() || tfAgencia.getText().isEmpty() ||
                    tfNumeroConta.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            if (!emailValido(tfEmail.getText())) {
                taLista.setText("E-mail inválido.");
                return;
            }
            if (!repo.emailDisponivel(tfEmail.getText(), null)) {
                taLista.setText("E-mail já cadastrado.");
                return;
            }
            Dono dono = new Dono(
                    tfNome.getText(),
                    tfEndereco.getText(),
                    tfEmail.getText(),
                    tfTelefone.getText(),
                    tfBanco.getText(),
                    tfAgencia.getText(),
                    tfNumeroConta.getText()
            );
            repo.salvar(dono);
            limparCampos();
            listarDonos();
        } catch (Exception ex) {
            taLista.setText("Erro ao inserir: " + ex.getMessage());
        }
    }

    private void buscarDono() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para buscar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            Dono dono = repo.buscarPorId(id);
            if (dono != null) {
                tfNome.setText(dono.getNome());
                tfEndereco.setText(dono.getEndereco());
                tfEmail.setText(dono.getEmail());
                tfTelefone.setText(dono.getTelefone());
                tfBanco.setText(dono.getBanco());
                tfAgencia.setText(dono.getAgencia());
                tfNumeroConta.setText(dono.getNumeroConta());
            } else {
                taLista.setText("Dono não encontrado.");
            }
        } catch (Exception e) {
            taLista.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void atualizarDono() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para atualizar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            if (tfNome.getText().isEmpty() || tfEndereco.getText().isEmpty() ||
                    tfEmail.getText().isEmpty() || tfTelefone.getText().isEmpty() ||
                    tfBanco.getText().isEmpty() || tfAgencia.getText().isEmpty() ||
                    tfNumeroConta.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            if (!emailValido(tfEmail.getText())) {
                taLista.setText("E-mail inválido.");
                return;
            }
            if (!repo.emailDisponivel(tfEmail.getText(), id)) {
                taLista.setText("E-mail já cadastrado para outro dono.");
                return;
            }
            Dono dono = new Dono(
                    tfNome.getText(),
                    tfEndereco.getText(),
                    tfEmail.getText(),
                    tfTelefone.getText(),
                    tfBanco.getText(),
                    tfAgencia.getText(),
                    tfNumeroConta.getText()
            );
            dono.setIdDono(id);
            repo.atualizar(dono);
            limparCampos();
            listarDonos();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirDono() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para excluir.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            repo.excluir(id);
            limparCampos();
            listarDonos();
        } catch (IOException ex) {
            taLista.setText("Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarDonos() {
        taLista.clear();
        for (Dono d : repo.listarTodos()) {
            taLista.appendText(d.toString() + "\n");
        }
    }

    private void limparCampos() {
        tfId.clear();
        tfNome.clear();
        tfEndereco.clear();
        tfEmail.clear();
        tfTelefone.clear();
        tfBanco.clear();
        tfAgencia.clear();
        tfNumeroConta.clear();
    }
}