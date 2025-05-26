module io.github.mateus.projetopoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.mateus.projetopoo to javafx.fxml;
    exports io.github.mateus.projetopoo;
}