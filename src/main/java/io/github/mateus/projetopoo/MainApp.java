package io.github.mateus.projetopoo;

import io.github.mateus.projetopoo.model.TipoUsuario;
import io.github.mateus.projetopoo.model.Usuario;
import io.github.mateus.projetopoo.repository.UsuarioRepository;
import io.github.mateus.projetopoo.view.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        boolean adminExiste = usuarioRepo.listarTodos().stream()
                .anyMatch(u -> u.getLogin().equals("admin"));
        if (!adminExiste) {
            Usuario admin = new Usuario("Administrador", "admin", "root", "admin@locmaq.com", TipoUsuario.GESTOR);
            usuarioRepo.salvar(admin);
        }

        primaryStage.setTitle("LocMac - Sistema de Gestão de Locação");

        VBox telaInicial = new VBox(20);
        telaInicial.setAlignment(Pos.CENTER);
        telaInicial.setPadding(new Insets(40));
        telaInicial.setBackground(new Background(new BackgroundFill(Color.web("#f3f4f6"), null, null)));

        Label titulo = new Label("LocMac");
        titulo.setFont(new Font("Arial", 42));
        titulo.setTextFill(Color.web("#263238"));

        Label subtitulo = new Label("Sistema de Gestão de Locação de Equipamentos");
        subtitulo.setFont(new Font("Arial", 18));
        subtitulo.setTextFill(Color.web("#374151"));

        Button btnPainelAdmin = new Button("Painel Admin");
        Button btnMenuPrincipal = new Button("Menu Principal");
        estilizarBotaoPrincipal(btnPainelAdmin);
        estilizarBotaoPrincipal(btnMenuPrincipal);

        telaInicial.getChildren().setAll(titulo, subtitulo, btnPainelAdmin, btnMenuPrincipal);

        Scene sceneInicial = new Scene(telaInicial, 700, 500);

        btnPainelAdmin.setOnAction(e -> {
            AutenticacaoView.mostrar(usuario -> {
                if (usuario.getTipo() == TipoUsuario.GESTOR && usuario.getLogin().equals("admin")) {
                    abrirJanela(new UsuarioView(), "Painel Admin - Gerenciar Usuário");
                } else {
                    mostrarAlerta("Acesso restrito ao administrador.");
                }
            });
        });

        btnMenuPrincipal.setOnAction(e -> {
            AutenticacaoView.mostrar(usuario -> {
                if (usuario.getTipo() == TipoUsuario.LOGISTICA) {
                    abrirMenuLogistica();
                } else if (usuario.getTipo() == TipoUsuario.PLANEJADOR) {
                    abrirMenuPlanejador();
                } else {
                    mostrarAlerta("Tipo de usuário sem acesso ao menu principal.");
                }
            });
        });

        primaryStage.setScene(sceneInicial);
        primaryStage.show();
    }

    private void abrirMenuLogistica() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        menu.setBackground(new Background(new BackgroundFill(Color.web("#f3f4f6"), null, null)));

        Label tituloMenu = new Label("Menu Logística");
        tituloMenu.setFont(new Font("Arial", 28));
        tituloMenu.setTextFill(Color.web("#263238"));

        Button btnEquipamento = criarBotaoCRUD("Gerenciar Equipamento", EquipamentoView.class);
        Button btnContrato = criarBotaoCRUD("Gerenciar Contrato de Locação", ContratoLocacaoView.class);

        menu.getChildren().addAll(tituloMenu, btnEquipamento, btnContrato);

        Stage stage = new Stage();
        stage.setTitle("Menu Logística");
        stage.setScene(new Scene(menu, 600, 400));
        stage.show();
    }

    private void abrirMenuPlanejador() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        menu.setBackground(new Background(new BackgroundFill(Color.web("#f3f4f6"), null, null)));

        Label tituloMenu = new Label("Menu Planejador");
        tituloMenu.setFont(new Font("Arial", 28));
        tituloMenu.setTextFill(Color.web("#263238"));

        Button btnBoletim = criarBotaoCRUD("Boletim de Medição", BoletimMedicaoView.class);
        Button btnCliente = criarBotaoCRUD("Gerenciar Cliente", ClienteView.class);
        Button btnDono = criarBotaoCRUD("Gerenciar Dono", DonoView.class);

        menu.getChildren().addAll(tituloMenu, btnBoletim, btnCliente, btnDono);

        Stage stage = new Stage();
        stage.setTitle("Menu Planejador");
        stage.setScene(new Scene(menu, 600, 400));
        stage.show();
    }

    private Button criarBotaoCRUD(String texto, Class<? extends VBox> viewClass) {
        Button btn = new Button(texto);
        estilizarBotaoCRUD(btn);
        btn.setOnAction(e -> {
            try {
                VBox view = viewClass.getDeclaredConstructor().newInstance();
                abrirJanela(view, texto);
            } catch (Exception ex) {
                mostrarAlerta("Erro ao abrir a tela: " + ex.getMessage());
            }
        });
        return btn;
    }

    private void abrirJanela(VBox view, String titulo) {
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(view, 600, 400));
        stage.show();
    }

    private void estilizarBotaoPrincipal(Button btn) {
        btn.setStyle("-fx-background-color: #374151; -fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-padding: 10px 20px; -fx-background-radius: 8px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-radius: 8px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #374151; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-radius: 8px;"));
    }

    private void estilizarBotaoCRUD(Button btn) {
        btn.setMinWidth(280);
        btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #374151; -fx-font-size: 15px; "
                + "-fx-padding: 10px 18px; -fx-border-color: #d1d5db; -fx-border-radius: 8px; "
                + "-fx-background-radius: 8px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #374151; "
                + "-fx-font-size: 15px; -fx-padding: 10px 18px; -fx-border-color: #d1d5db; "
                + "-fx-border-radius: 8px; -fx-background-radius: 8px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #374151; "
                + "-fx-font-size: 15px; -fx-padding: 10px 18px; -fx-border-color: #d1d5db; "
                + "-fx-border-radius: 8px; -fx-background-radius: 8px;"));
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}