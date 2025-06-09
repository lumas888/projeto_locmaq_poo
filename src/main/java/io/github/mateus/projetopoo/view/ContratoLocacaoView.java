package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.ContratoLocacao;
import io.github.mateus.projetopoo.model.Cliente;
import io.github.mateus.projetopoo.model.Equipamento;
import io.github.mateus.projetopoo.repository.ContratoLocacaoRepository;
import io.github.mateus.projetopoo.repository.ClienteRepository;
import io.github.mateus.projetopoo.repository.EquipamentoRepository;
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
    private ComboBox<Cliente> cbCliente = new ComboBox<>();
    private ComboBox<Equipamento> cbEquipamento = new ComboBox<>();

    private ContratoLocacaoRepository repo = new ContratoLocacaoRepository();
    private ClienteRepository clienteRepo = new ClienteRepository();
    private EquipamentoRepository equipamentoRepo = new EquipamentoRepository();

    public ContratoLocacaoView() {
        setSpacing(10);
        setPadding(new Insets(15));

        tfId.setPromptText("ID (para buscar, atualizar ou excluir)");
        tfDataInicio.setPromptText("Data Início");
        tfDataFim.setPromptText("Data Fim");
        tfDescricao.setPromptText("Descrição");
        tfValor.setPromptText("Valor");
        cbCliente.setPromptText("Selecione o Cliente");
        cbEquipamento.setPromptText("Selecione o Equipamento");

        cbCliente.getItems().addAll(clienteRepo.listarTodos());
        cbEquipamento.getItems().addAll(equipamentoRepo.listarTodos());

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

        getChildren().addAll(
                new HBox(10, tfId, btnBuscar, btnAtualizar, btnExcluir),
                cbCliente, cbEquipamento,
                tfDataInicio, tfDataFim, tfDescricao, tfValor,
                new HBox(10, btnSalvar, btnListar),
                taLista
        );
    }

    private void inserirContrato() {
        try {
            if (tfDataInicio.getText().isEmpty() || tfDataFim.getText().isEmpty() ||
                    tfDescricao.getText().isEmpty() || tfValor.getText().isEmpty() ||
                    cbCliente.getValue() == null || cbEquipamento.getValue() == null) {
                taLista.setText("Preencha todos os campos obrigatórios, incluindo cliente e equipamento.");
                return;
            }
            java.time.LocalDate dataInicio, dataFim;
            try {
                dataInicio = java.time.LocalDate.parse(tfDataInicio.getText());
                dataFim = java.time.LocalDate.parse(tfDataFim.getText());
            } catch (java.time.format.DateTimeParseException ex) {
                taLista.setText("Data inválida. Use o formato yyyy-MM-dd.");
                return;
            }
            if (dataInicio.isAfter(dataFim)) {
                taLista.setText("A data de início não pode ser após a data de fim.");
                return;
            }
            double valor;
            try {
                valor = Double.parseDouble(tfValor.getText());
            } catch (NumberFormatException ex) {
                taLista.setText("Valor inválido.");
                return;
            }
            ContratoLocacao contrato = new ContratoLocacao(
                    tfDataInicio.getText(),
                    tfDataFim.getText(),
                    tfDescricao.getText(),
                    valor,
                    cbCliente.getValue(),
                    cbEquipamento.getValue()
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
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para buscar.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            ContratoLocacao contrato = repo.buscarPorId(id);
            if (contrato != null) {
                tfDataInicio.setText(contrato.getDataInicio());
                tfDataFim.setText(contrato.getDataFim());
                tfDescricao.setText(contrato.getDescricao());
                tfValor.setText(String.valueOf(contrato.getValor()));
                cbCliente.getSelectionModel().select(contrato.getCliente());
                cbEquipamento.getSelectionModel().select(contrato.getEquipamento());
            } else {
                taLista.setText("Contrato não encontrado.");
            }
        } catch (Exception e) {
            taLista.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void atualizarContrato() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para atualizar.");
                return;
            }
            if (tfDataInicio.getText().isEmpty() || tfDataFim.getText().isEmpty() ||
                    tfDescricao.getText().isEmpty() || tfValor.getText().isEmpty() ||
                    cbCliente.getValue() == null || cbEquipamento.getValue() == null) {
                taLista.setText("Preencha todos os campos obrigatórios, incluindo cliente e equipamento.");
                return;
            }
            java.time.LocalDate dataInicio, dataFim;
            try {
                dataInicio = java.time.LocalDate.parse(tfDataInicio.getText());
                dataFim = java.time.LocalDate.parse(tfDataFim.getText());
            } catch (java.time.format.DateTimeParseException ex) {
                taLista.setText("Data inválida. Use o formato yyyy-MM-dd.");
                return;
            }
            if (dataInicio.isAfter(dataFim)) {
                taLista.setText("A data de início não pode ser após a data de fim.");
                return;
            }
            double valor;
            try {
                valor = Double.parseDouble(tfValor.getText());
            } catch (NumberFormatException ex) {
                taLista.setText("Valor inválido.");
                return;
            }
            int id = Integer.parseInt(tfId.getText());
            ContratoLocacao contrato = new ContratoLocacao(
                    tfDataInicio.getText(),
                    tfDataFim.getText(),
                    tfDescricao.getText(),
                    valor,
                    cbCliente.getValue(),
                    cbEquipamento.getValue()
            );
            contrato.setId(id);
            repo.atualizar(contrato);
            limparCampos();
            listarContratos();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirContrato() {
        try {
            if (tfId.getText().isEmpty()) {
                taLista.setText("Informe o ID para excluir.");
                return;
            }
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
        cbCliente.getSelectionModel().clearSelection();
        cbEquipamento.getSelectionModel().clearSelection();
    }
}

