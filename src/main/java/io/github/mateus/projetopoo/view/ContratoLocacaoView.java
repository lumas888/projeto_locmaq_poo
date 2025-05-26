package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.ContratoLocacao;
import io.github.mateus.projetopoo.repository.ContratoLocacaoRepository;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.io.IOException;

public class ContratoLocacaoView extends VBox {
    private TextField tfId = new TextField();
    private TextField tfDataInicio = new TextField();
    private TextField tfDataFim = new TextField();
    private TextField tfDescricao = new TextField();
    private TextField tfValor = new TextField();
    private TextArea taLista = new TextArea();

    private ContratoLocacaoRepository repo = new ContratoLocacaoRepository();

    public ContratoLocacaoView() {
        setSpacing(10);
        setPadding(new Insets(15));

        tfId.setPromptText("ID");
        tfDataInicio.setPromptText("Data Início");
        tfDataFim.setPromptText("Data Fim");
        tfDescricao.setPromptText("Descrição");
        tfValor.setPromptText("Valor");

        taLista.setPrefRowCount(10);
        taLista.setEditable(false);

        Button btnSalvar = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnListar = new Button("Listar Todos");

        btnSalvar.setOnAction(e -> inserirContrato());
        btnBuscar.setOnAction(e -> buscarContrato());
        btnAtualizar.setOnAction(e -> atualizarContrato());
        btnExcluir.setOnAction(e -> excluirContrato());
        btnListar.setOnAction(e -> listarContratos());

        getChildren().addAll(tfId, tfDataInicio, tfDataFim, tfDescricao, tfValor,
                new HBox(10, btnSalvar, btnBuscar, btnAtualizar, btnExcluir, btnListar),
                taLista);
    }

    private void inserirContrato() {
        try {
            ContratoLocacao contrato = new ContratoLocacao(
                    Integer.parseInt(tfId.getText()),
                    tfDataInicio.getText(),
                    tfDataFim.getText(),
                    tfDescricao.getText(),
                    Double.parseDouble(tfValor.getText())
            );
            repo.salvar(contrato);
            limparCampos();
            listarContratos();
        } catch (Exception ex) {
            taLista.setText("Erro ao inserir: " + ex.getMessage());
        }
    }

    private void buscarContrato() {
        try {
            int id = Integer.parseInt(tfId.getText());
            ContratoLocacao contrato = repo.buscarPorId(id);
            if (contrato != null) {
                tfDataInicio.setText(contrato.getDataInicio());
                tfDataFim.setText(contrato.getDataFim());
                tfDescricao.setText(contrato.getDescricao());
                tfValor.setText(String.valueOf(contrato.getValor()));
            } else {
                taLista.setText("Contrato não encontrado.");
            }
        } catch (Exception e) {
            taLista.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void atualizarContrato() {
        try {
            ContratoLocacao contrato = new ContratoLocacao(
                    Integer.parseInt(tfId.getText()),
                    tfDataInicio.getText(),
                    tfDataFim.getText(),
                    tfDescricao.getText(),
                    Double.parseDouble(tfValor.getText())
            );
            repo.atualizar(contrato);
            limparCampos();
            listarContratos();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirContrato() {
        try {
            int id = Integer.parseInt(tfId.getText());
            repo.excluir(id);
            limparCampos();
            listarContratos();
        } catch (IOException ex) {
            taLista.setText("Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarContratos() {
        taLista.clear();
        for (ContratoLocacao c : repo.listarTodos()) {
            taLista.appendText(c.toString() + "\n");
        }
    }

    private void limparCampos() {
        tfId.clear();
        tfDataInicio.clear();
        tfDataFim.clear();
        tfDescricao.clear();
        tfValor.clear();
    }
}
