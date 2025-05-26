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

        tfId.setPromptText("ID");
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

        getChildren().addAll(tfId, tfDescricao, tfData, tfValorMedido,
                new HBox(10, btnSalvar, btnBuscar, btnAtualizar, btnExcluir, btnListar),
                taLista);
    }

    private void inserirBoletim() {
        try {
            BoletimMedicao boletim = new BoletimMedicao(
                    Integer.parseInt(tfId.getText()),
                    tfDescricao.getText(),
                    tfData.getText(),
                    Double.parseDouble(tfValorMedido.getText())
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
            BoletimMedicao boletim = new BoletimMedicao(
                    Integer.parseInt(tfId.getText()),
                    tfDescricao.getText(),
                    tfData.getText(),
                    Double.parseDouble(tfValorMedido.getText())
            );
            repo.atualizar(boletim);
            limparCampos();
            listarBoletins();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirBoletim() {
        try {
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
