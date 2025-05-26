package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.BoletimMedicao;
import io.github.mateus.projetopoo.repository.BoletimMedicaoRepository;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.io.IOException;

public class BoletimMedicaoView extends VBox {
    private TextField tfId = new TextField();
    private TextField tfDescricao = new TextField();
    private TextField tfData = new TextField();
    private TextField tfValorMedido = new TextField();
    private TextArea taLista = new TextArea();

    private BoletimMedicaoRepository repo = new BoletimMedicaoRepository();

    public BoletimMedicaoView() {
        setSpacing(10);
        setPadding(new Insets(15));

        tfId.setPromptText("ID (para buscar, atualizar ou excluir)");
        tfDescricao.setPromptText("Descrição");
        tfData.setPromptText("Data");
        tfValorMedido.setPromptText("Valor Medido");

        taLista.setPrefRowCount(10);
        taLista.setEditable(false);

        Button btnSalvar = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnListar = new Button("Listar Todos");

        btnSalvar.setOnAction(e -> inserirBoletim());
        btnBuscar.setOnAction(e -> buscarBoletim());
        btnAtualizar.setOnAction(e -> atualizarBoletim());
        btnExcluir.setOnAction(e -> excluirBoletim());
        btnListar.setOnAction(e -> listarBoletins());

        getChildren().addAll(
                new HBox(10, tfId, btnBuscar, btnAtualizar, btnExcluir),
                tfDescricao, tfData, tfValorMedido,
                new HBox(10, btnSalvar, btnListar),
                taLista
        );
    }

    private void inserirBoletim() {
        try {
            if (tfDescricao.getText().isEmpty() || tfData.getText().isEmpty() || tfValorMedido.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            double valor;
            try {
                valor = Double.parseDouble(tfValorMedido.getText());
            } catch (NumberFormatException e) {
                taLista.setText("Valor Medido inválido.");
                return;
            }
            BoletimMedicao boletim = new BoletimMedicao(
                    tfDescricao.getText(),
                    tfData.getText(),
                    valor
            );
            repo.salvar(boletim);
            limparCampos();
            listarBoletins();
        } catch (Exception ex) {
            taLista.setText("Erro ao inserir: " + ex.getMessage());
        }
    }

    private void buscarBoletim() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para buscar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            BoletimMedicao boletim = repo.buscarPorId(id);
            if (boletim != null) {
                tfDescricao.setText(boletim.getDescricao());
                tfData.setText(boletim.getData());
                tfValorMedido.setText(String.valueOf(boletim.getValorMedido()));
            } else {
                taLista.setText("Boletim não encontrado.");
            }
        } catch (Exception e) {
            taLista.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void atualizarBoletim() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para atualizar.");
                return;
            }
            if (tfDescricao.getText().isEmpty() || tfData.getText().isEmpty() || tfValorMedido.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            double valor;
            try {
                valor = Double.parseDouble(tfValorMedido.getText());
            } catch (NumberFormatException e) {
                taLista.setText("Valor Medido inválido.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            BoletimMedicao boletim = new BoletimMedicao(
                    tfDescricao.getText(),
                    tfData.getText(),
                    valor
            );
            boletim.setId(id);
            repo.atualizar(boletim);
            limparCampos();
            listarBoletins();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirBoletim() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para excluir.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            repo.excluir(id);
            limparCampos();
            listarBoletins();
        } catch (IOException ex) {
            taLista.setText("Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarBoletins() {
        taLista.clear();
        for (BoletimMedicao b : repo.listarTodos()) {
            taLista.appendText(b.toString() + "\n");
        }
    }

    private void limparCampos() {
        tfId.clear();
        tfDescricao.clear();
        tfData.clear();
        tfValorMedido.clear();
    }
}