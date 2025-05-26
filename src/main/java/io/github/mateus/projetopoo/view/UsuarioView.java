package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.TipoUsuario;
import io.github.mateus.projetopoo.model.Usuario;
import io.github.mateus.projetopoo.repository.UsuarioRepository;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.io.IOException;

public class UsuarioView extends VBox {
    private TextField tfId = new TextField();
    private TextField tfNome = new TextField();
    private TextField tfLogin = new TextField();
    private PasswordField tfSenha = new PasswordField();
    private TextField tfEmail = new TextField();
    private ComboBox<TipoUsuario> cbTipo = new ComboBox<>();
    private TextArea taLista = new TextArea();

    private UsuarioRepository repo = new UsuarioRepository();

    public UsuarioView() {
        setSpacing(10);
        setPadding(new Insets(15));

        tfId.setPromptText("ID");
        tfNome.setPromptText("Nome");
        tfLogin.setPromptText("Login");
        tfSenha.setPromptText("Senha");
        tfEmail.setPromptText("Email");
        cbTipo.getItems().addAll(TipoUsuario.values());
        cbTipo.setPromptText("Tipo de Usuário");

        taLista.setPrefRowCount(10);
        taLista.setEditable(false);

        Button btnSalvar = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnListar = new Button("Listar Todos");

        btnSalvar.setOnAction(e -> inserirUsuario());
        btnBuscar.setOnAction(e -> buscarUsuario());
        btnAtualizar.setOnAction(e -> atualizarUsuario());
        btnExcluir.setOnAction(e -> excluirUsuario());
        btnListar.setOnAction(e -> listarUsuarios());

        getChildren().addAll(tfId, tfNome, tfLogin, tfSenha, tfEmail, cbTipo,
                new HBox(10, btnSalvar, btnBuscar, btnAtualizar, btnExcluir, btnListar),
                taLista);
    }

    private void inserirUsuario() {
        try {
            Usuario usuario = new Usuario(
                    Integer.parseInt(tfId.getText()),
                    tfNome.getText(),
                    tfLogin.getText(),
                    tfSenha.getText(),
                    tfEmail.getText(),
                    cbTipo.getValue()
            );
            repo.salvar(usuario);
            limparCampos();
            listarUsuarios();
        } catch (Exception ex) {
            taLista.setText("Erro ao inserir: " + ex.getMessage());
        }
    }

    private void buscarUsuario() {
        try {
            int id = Integer.parseInt(tfId.getText());
            Usuario usuario = repo.buscarPorId(id);
            if (usuario != null) {
                tfNome.setText(usuario.getNome());
                tfLogin.setText(usuario.getLogin());
                tfSenha.setText(usuario.getSenha());
                tfEmail.setText(usuario.getEmail());
                cbTipo.setValue(usuario.getTipo());
            } else {
                taLista.setText("Usuário não encontrado.");
            }
        } catch (Exception e) {
            taLista.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void atualizarUsuario() {
        try {
            Usuario usuario = new Usuario(
                    Integer.parseInt(tfId.getText()),
                    tfNome.getText(),
                    tfLogin.getText(),
                    tfSenha.getText(),
                    tfEmail.getText(),
                    cbTipo.getValue()
            );
            repo.atualizar(usuario);
            limparCampos();
            listarUsuarios();
        } catch (Exception ex) {
            taLista.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirUsuario() {
        try {
            int id = Integer.parseInt(tfId.getText());
            repo.excluir(id);
            limparCampos();
            listarUsuarios();
        } catch (IOException ex) {
            taLista.setText("Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarUsuarios() {
        taLista.clear();
        for (Usuario u : repo.listarTodos()) {
            taLista.appendText(u.toString() + "\n");
        }
    }

    private void limparCampos() {
        tfId.clear();
        tfNome.clear();
        tfLogin.clear();
        tfSenha.clear();
        tfEmail.clear();
        cbTipo.setValue(null);
    }
}
