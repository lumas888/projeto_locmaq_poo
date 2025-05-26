package io.github.mateus.projetopoo.view;

import io.github.mateus.projetopoo.model.Usuario;
import io.github.mateus.projetopoo.repository.UsuarioRepository;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AutenticacaoView extends VBox {
    public interface AutenticacaoCallback {
        void onAutenticado(Usuario usuario);
    }

    public AutenticacaoView(AutenticacaoCallback callback) {
        setSpacing(10);
        setPadding(new Insets(20));
        TextField tfLogin = new TextField();
        tfLogin.setPromptText("Login");
        PasswordField pfSenha = new PasswordField();
        pfSenha.setPromptText("Senha");
        Button btnEntrar = new Button("Entrar");
        Label lblMensagem = new Label();

        btnEntrar.setOnAction(e -> {
            UsuarioRepository repo = new UsuarioRepository();
            Usuario usuario = repo.autenticar(tfLogin.getText(), pfSenha.getText());
            if (usuario != null) {
                callback.onAutenticado(usuario);
                ((Stage) getScene().getWindow()).close();
            } else {
                lblMensagem.setText("Login ou senha inválidos.");
            }
        });

        getChildren().addAll(new Label("Autenticação"), tfLogin, pfSenha, btnEntrar, lblMensagem);
    }

    public static void mostrar(AutenticacaoCallback callback) {
        Stage stage = new Stage();
        stage.setScene(new Scene(new AutenticacaoView(callback), 300, 200));
        stage.setTitle("Autenticação");
        stage.show();
    }
}