package io.github.mateus.projetopoo;

import io.github.mateus.projetopoo.view.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LocMac - Sistema de Gestão de Locação");

        // Tela de Boas-Vindas
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

        Button btnEntrar = new Button("Acessar Sistema");
        estilizarBotaoPrincipal(btnEntrar);

        telaInicial.getChildren().addAll(titulo, subtitulo, btnEntrar);

        Scene sceneInicial = new Scene(telaInicial, 700, 500);

        // Tela Menu Principal
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        menu.setBackground(new Background(new BackgroundFill(Color.web("#f3f4f6"), null, null)));

        Label tituloMenu = new Label("Menu Principal - LocMac");
        tituloMenu.setFont(new Font("Arial", 28));
        tituloMenu.setTextFill(Color.web("#263238"));

        Button btnCliente = new Button("CRUD Cliente");
        Button btnEquipamento = new Button("CRUD Equipamento");
        Button btnUsuario = new Button("CRUD Usuário");
        Button btnDono = new Button("CRUD Dono");
        Button btnBoletim = new Button("CRUD Boletim de Medição");
        Button btnContrato = new Button("CRUD Contrato de Locação");

        estilizarBotaoCRUD(btnCliente);
        estilizarBotaoCRUD(btnEquipamento);
        estilizarBotaoCRUD(btnUsuario);
        estilizarBotaoCRUD(btnDono);
        estilizarBotaoCRUD(btnBoletim);
        estilizarBotaoCRUD(btnContrato);

        menu.getChildren().addAll(tituloMenu,
                btnCliente, btnEquipamento, btnUsuario,
                btnDono, btnBoletim, btnContrato);

        Scene sceneMenu = new Scene(menu, 700, 500);

        // Ações
        btnEntrar.setOnAction(e -> primaryStage.setScene(sceneMenu));

        btnCliente.setOnAction(e -> abrirJanela(new ClienteView(), "CRUD Cliente"));
        btnEquipamento.setOnAction(e -> abrirJanela(new EquipamentoView(), "CRUD Equipamento"));
        btnUsuario.setOnAction(e -> abrirJanela(new UsuarioView(), "CRUD Usuário"));
        btnDono.setOnAction(e -> abrirJanela(new DonoView(), "CRUD Dono"));
        btnBoletim.setOnAction(e -> abrirJanela(new BoletimMedicaoView(), "CRUD Boletim de Medição"));
        btnContrato.setOnAction(e -> abrirJanela(new ContratoLocacaoView(), "CRUD Contrato de Locação"));

        primaryStage.setScene(sceneInicial);
        primaryStage.show();
    }

    // Método para abrir as telas dos CRUDs
    private void abrirJanela(VBox view, String titulo) {
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(view, 600, 400));
        stage.show();
    }

    // Estilo dos botões principais
    private void estilizarBotaoPrincipal(Button btn) {
        btn.setStyle("-fx-background-color: #374151; -fx-text-fill: white; -fx-font-size: 16px; "
                + "-fx-padding: 10px 20px; -fx-background-radius: 8px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-radius: 8px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #374151; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-radius: 8px;"));
    }

    // Estilo dos botões do menu CRUD
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

    public static void main(String[] args) {
        launch(args);
    }
}
