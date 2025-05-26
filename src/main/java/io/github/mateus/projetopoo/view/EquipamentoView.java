package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.Equipamento;
import io.github.mateus.projetopoo.repository.EquipamentoRepository;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EquipamentoView extends VBox {
    private TextField tfId = new TextField();
    private TextField tfNome = new TextField();
    private TextField tfDescricao = new TextField();
    private TextField tfFabricante = new TextField();
    private TextField tfNumeroSerie = new TextField();
    private TextArea taLista = new TextArea();

    private EquipamentoRepository repo = new EquipamentoRepository();

    public EquipamentoView() {
        setSpacing(10);
        setPadding(new Insets(15));

        tfId.setPromptText("ID (para buscar, atualizar ou excluir)");
        tfNome.setPromptText("Nome");
        tfDescricao.setPromptText("Descrição");
        tfFabricante.setPromptText("Fabricante");
        tfNumeroSerie.setPromptText("Número de Série");
        taLista.setPrefRowCount(10);
        taLista.setEditable(false);

        Button btnSalvar = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnListar = new Button("Listar Todos");

        btnSalvar.setOnAction(e -> inserirEquipamento());
        btnBuscar.setOnAction(e -> buscarEquipamento());
        btnAtualizar.setOnAction(e -> atualizarEquipamento());
        btnExcluir.setOnAction(e -> excluirEquipamento());
        btnListar.setOnAction(e -> listarEquipamentos());

        getChildren().addAll(
                new HBox(10, tfId, btnBuscar, btnAtualizar, btnExcluir),
                tfNome, tfDescricao, tfFabricante, tfNumeroSerie,
                new HBox(10, btnSalvar, btnListar),
                taLista
        );
    }

    private void inserirEquipamento() {
        try {
            if (tfNome.getText().isEmpty() || tfDescricao.getText().isEmpty() ||
                    tfFabricante.getText().isEmpty() || tfNumeroSerie.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            if (!repo.numeroSerieDisponivel(tfNumeroSerie.getText(), null)) {
                taLista.setText("Número de série já cadastrado.");
                return;
            }
            Equipamento equipamento = new Equipamento(
                    tfNome.getText(),
                    tfDescricao.getText(),
                    tfFabricante.getText(),
                    tfNumeroSerie.getText()
            );
            repo.salvar(equipamento);
            limparCampos();
            listarEquipamentos();
        } catch (Exception ex) {
            taLista.setText("Erro ao inserir: " + ex.getMessage());
        }
    }

    private void buscarEquipamento() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para buscar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            Equipamento equipamento = repo.buscarPorId(id);
            if (equipamento != null) {
                tfNome.setText(equipamento.getNome());
                tfDescricao.setText(equipamento.getDescricao());
                tfFabricante.setText(equipamento.getFabricante());
                tfNumeroSerie.setText(equipamento.getNumeroSerie());
            } else {
                taLista.setText("Equipamento não encontrado.");
            }
        } catch (Exception e) {
            taLista.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void atualizarEquipamento() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para atualizar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            if (tfNome.getText().isEmpty() || tfDescricao.getText().isEmpty() ||
                    tfFabricante.getText().isEmpty() || tfNumeroSerie.getText().isEmpty()) {
                taLista.setText("Preencha todos os campos.");
                return;
            }
            if (!repo.numeroSerieDisponivel(tfNumeroSerie.getText(), id)) {
                taLista.setText("Número de série já cadastrado para outro equipamento.");
                return;
            }
            Equipamento equipamento = new Equipamento(
                    tfNome.getText(),
                    tfDescricao.getText(),
                    tfFabricante.getText(),
                    tfNumeroSerie.getText()
            );
            equipamento.setIdEquipamento(id);
            repo.atualizar(equipamento);
            limparCampos();
            listarEquipamentos();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirEquipamento() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para excluir.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            repo.excluir(id);
            limparCampos();
            listarEquipamentos();
        } catch (IOException ex) {
            taLista.setText("Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarEquipamentos() {
        taLista.clear();
        for (Equipamento e : repo.listarTodos()) {
            taLista.appendText(e.toString() + "\n");
        }
    }

    private void limparCampos() {
        tfId.clear();
        tfNome.clear();
        tfDescricao.clear();
        tfFabricante.clear();
        tfNumeroSerie.clear();
    }
}